(defn constant [arg & vars] (constantly arg))

(defn variable [arg] (fn [vars] (get vars arg)))

(defn function-operation [f]
  (fn [& args]
    (fn [vars] (apply f (mapv (fn [arg] (arg vars)) args)))
    )
  )

(def negate (function-operation -))
(def add (function-operation +))
(def subtract (function-operation -))
(def multiply (function-operation *))
(def divide-impl (fn [& args]
                   (cond
                     (empty? args) (double 1)
                     (empty? (rest args)) (/ (double 1) (first args))
                     :else (/ (double (first args)) (double (apply * (rest args)))))
                   ))
(def divide (function-operation divide-impl))
(defn sumexp-impl [& args]
  (apply + (mapv (fn [arg] (Math/exp (double arg))) args)))
(def sumexp (function-operation sumexp-impl))
(def lse (function-operation (fn [& args] (Math/log (double (apply sumexp-impl args))))))


(def operations
  {
   'constant constant
   'variable variable
   'negate   negate,
   '+        add,
   '-        subtract,
   '*        multiply,
   '/        divide,
   'sumexp   sumexp,
   'lse      lse
   })

(defn proto-get
  ([obj key] (proto-get obj key nil))
  ([obj key default]
   (cond
     (contains? obj key) (obj key)
     (contains? obj :prototype) (proto-get (obj :prototype) key default)
     :else default)))

(defn proto-call
  [this key & args]
  (apply (proto-get this key) this args))

(defn field
  [key] (fn
          ([this] (proto-get this key))
          ([this def] (proto-get this key def))))

(defn method
  [key] (fn [this & args] (apply proto-call this key args)))

(def value (field :value))
(def toString (method :toString))
(def toStringInfix (method :toStringInfix))
(def evaluate (method :evaluate))
(def diff (method :diff))
(declare ZERO)
(def ConstantProto {
                    :evaluate      (fn [this vars] (value this))
                    :toString      (fn [this] (str (value this)))
                    :toStringInfix (fn [this] (str (value this)))
                    :diff          (fn [this var] ZERO)
                    })

(defn Constant [value] {:prototype ConstantProto
                        :value     value
                        }
  )
(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(def VariableProto {
                    :evaluate      (fn [this vars] (get vars (value this)))
                    :toString      (fn [this] (value this))
                    :toStringInfix (fn [this] (value this))
                    :diff          (fn [this var] (if (= var (value this)) ONE ZERO))
                    })
(defn Variable [value] {:prototype VariableProto
                        :value     value})

(def operation (field :operation))
(def operands (field :operands))
(def operation-string (field :operation-string))
(def diff-impl (method :diff-impl))
(def Operation {
                :evaluate      (fn [this vars] (apply (operation this)
                                                      (mapv (fn [operand] (evaluate operand vars)) (operands this))))
                :toString      (fn [this] (str "(" (operation-string this) " "
                                               (clojure.string/join " " (mapv toString (operands this))) ")"))
                :diff          (fn [this var] (diff-impl this var))
                :toStringInfix (fn [this] (str "("
                                               (clojure.string/join (str " " (operation-string this) " ")
                                                                    (mapv toStringInfix (operands this))) ")"))
                })
(defn operation-constructor
  [operation operation-string diff-impl]
  (fn [& operands] {:prototype        Operation
                    :operation        operation
                    :operation-string operation-string
                    :diff-impl        diff-impl
                    :operands         operands}))
(defn operands-diff [this var] (mapv (fn [operand] (diff operand var)) (operands this)))

(defn last-priority-diff [f this var]
  (apply f (operands-diff this var)))
(def Negate (operation-constructor - "negate" (fn [this var] (last-priority-diff Negate this var))))
(def Add (operation-constructor + "+" (fn [this var] (last-priority-diff Add this var))))

(def Subtract (operation-constructor - "-" (fn [this var] (last-priority-diff Subtract this var))))
(declare Multiply)
(defn prod-rest [this] (apply Multiply (rest (operands this))))

(def Multiply
  (operation-constructor * "*" (fn [this var]
                                 (if (empty? (rest (operands this)))
                                   (diff (first (operands this)) var)
                                   (Add (Multiply (diff (first (operands this)) var)
                                                  (prod-rest this)
                                                  )
                                        (Multiply (first (operands this))
                                                  (diff (prod-rest this) var)))
                                   )
                                 )))
(def Divide
  (operation-constructor divide-impl "/"
                         (fn [this var]
                           (cond
                             (empty? (operands this)) ONE
                             (empty? (rest (operands this)))
                             (Divide (diff (Negate (first (operands this))) var)
                                     (Multiply (first (operands this)) (first (operands this))))
                             :else
                             (Divide (Subtract (Multiply (diff (first (operands this)) var) (prod-rest this))
                                               (Multiply (first (operands this))
                                                         (diff (prod-rest this) var)))
                                     (Multiply (prod-rest this) (prod-rest this)))
                             )
                           )))
(defn meansq-impl [& args] (/ (apply + (mapv (fn [arg] (* arg arg)) args)) (count args)))
(def Meansq (operation-constructor meansq-impl
                                   "meansq"
                                   (fn [this var]
                                     (Divide (apply Add (mapv (fn [operand] (diff (Multiply operand operand) var))
                                                              (operands this))
                                                    ) (Constant (count (operands this))))
                                     )
                                   ))
(def RMS (operation-constructor (fn [& args] (Math/sqrt
                                               (double (apply meansq-impl args))))
                                "rms" (fn [this var]
                                        (Divide (diff (apply Meansq (operands this)) var)
                                                (Multiply (apply RMS (operands this)) TWO)))))

(def object-operations
  {
   'constant Constant
   'variable Variable
   '+        Add
   '-        Subtract
   '*        Multiply
   '/        Divide
   'negate   Negate
   'meansq   Meansq
   'rms      RMS
   })

(defn parse-string [expr set-func]
  (cond
    (seq? expr) (apply (set-func (first expr)) (mapv (fn [arg] (parse-string arg set-func)) (rest expr)))
    (number? expr) ((set-func 'constant) expr)
    :else ((set-func 'variable) (str expr))))

(defn parseObject [expression] (parse-string (read-string expression) object-operations))
(defn parseFunction [expression] (parse-string (read-string expression) operations))

(defn parse-string-infix [expr] (Constant 10.0))
(defn parseObjectInfix [expression] (parse-string-infix (read-string expression)))
;(println (toStringInfix (Multiply (Add ONE TWO) ONE TWO)))


