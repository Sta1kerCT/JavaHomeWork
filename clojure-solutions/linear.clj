(defn correct-scalar [n] (every? number? n))

(defn correct-vector [vect]
  (and (vector? vect) (every? number? vect)))

(defn size-equals [element1 element2]
  (= (count element1) (count element2))
  )

(defn vsize-equals [vectors]
  (every? (fn [vect] (and (correct-vector vect)
                          (size-equals (first vectors) vect)
                          )
            ) vectors
          )
  )

(defn correct-matrix [matrix] (and (vector? matrix) (vsize-equals matrix)))

(defn msize-equals [matrices]
  (every? (fn [matrix]
            (and (correct-matrix matrix)
                 (and (size-equals (first (first matrices)) (first matrix))
                      (size-equals (first matrices) matrix)
                      )
                 )
            ) matrices
          )
  )

(defn vector-operation [f & vectors]
  {:pre [(every? correct-vector vectors) (vsize-equals vectors)]
   :post [(correct-vector %)]}
  (apply mapv f vectors)
  )

(def v+ (partial vector-operation +))
(def v- (partial vector-operation -))
(def v* (partial vector-operation *))
(def vd (partial vector-operation /))

(defn v*s [v & s]
  {:pre [(correct-vector v) ((fn [x] (correct-scalar x)) s)]
   :post [(correct-vector %)]
   }
  (def prod-s (apply * s))
  (mapv (partial * prod-s) v)
  )

(defn scalar [& vectors]
  {:pre [(every? correct-vector vectors) (vsize-equals vectors)]
   :post [(number? %)]}
  (apply + (apply v* vectors))
  )

(defn determinant [x v1 y v2] (- (* (nth x v1) (nth y v2)) (* (nth y v1) (nth x v2))))

(defn vect [& vectors]
  {:pre [(every? correct-vector vectors) (vsize-equals vectors)]
   :post [correct-vector %]}
  (reduce (fn [x y] (vector (determinant x 1 y 2)
                            (determinant x 2 y 0)
                            (determinant x 0 y 1))) vectors)
  )

(defn matrix-operation [f & matrices]
  {:pre [(every? correct-matrix matrices) (msize-equals matrices)]
   :post [(correct-matrix %)]}
  (apply mapv (partial vector-operation f) matrices)
  )

(def m+ (partial matrix-operation +))
(def m- (partial matrix-operation -))
(def m* (partial matrix-operation *))
(def md (partial matrix-operation /))


(defn m*s [m & s]
  {:pre [(correct-matrix m) ((fn [n] (correct-scalar n)) s)]
   :post [(correct-matrix %)]}
  (def prod-s (apply * s))
  (mapv (fn [vect] (v*s vect prod-s)) m)
  )

(defn m*v [matrix & v]
  {:pre [(correct-matrix matrix) (every? correct-vector v)]}
  (mapv (apply partial scalar v) matrix)
  )

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn m*m [& matrices]
  {:pre [(every? correct-matrix matrices)]
   :post [(correct-matrix %)]}
  (reduce (fn [x y]
            (def transpose-y (transpose y))
            (mapv (fn [vect] (m*v (transpose y) vect)) x))
          matrices))

(defn tsize-equals [tensors]
  (or (every? number? tensors)
      (apply = (mapv count tensors))
      )
  )

(defn tensor-operation [f & tensors]
  {:pre [(tsize-equals tensors)]}
  (if (every? number? tensors)
    (apply f tensors)
    (if (every? correct-vector tensors) (apply (partial vector-operation f) tensors)
                                        (apply mapv (partial tensor-operation f) tensors))
    )
  )

(def t+ (partial tensor-operation +))
(def t- (partial tensor-operation -))
(def t* (partial tensor-operation *))
(def td (partial tensor-operation /))