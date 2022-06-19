(ns uk.axvr.idna.punycode)


;; TODO: create generic bootstring algorithm implementation + just have
;; punycode algo pass correct parameters.


(defn- encode-digit
  "Returns the basic code point whose value (when used for representing
  integers) is d, which needs to be in the range 0 to base-1.  The lower-case
  form is used unless the \"upper-case?\" flag is truthy.
  
  Maps 0–25 to a–z and 26–35 to 0–9."
  ([d]
   (char
     (+ d (if (< d 26)
            97
            22))))
  ([d upper-case?]
   (encode-digit
     (if (and upper-case? (< d 26))
       (- d 32)
       d))))


(defn- decode-digit
  "Returns the numeric value of a basic code point (for use in representing
  integers) in the range 0 to base-1, or base (36) if cp does not represent
  a value.
  
  Maps a–z (and A–Z) to 0–25 and 0–9 to 26–35."
  [cp]
  (let [cp (int cp)]
    (cond
      (< (- cp 48) 10) (- cp 22)
      (< (- cp 65) 26) (- cp 65)
      (< (- cp 97) 26) (- cp 97)
      :else 36)))


(defn- decode-gvli [s]
  (let [thres [1 1 26]
        units (first s)
        sec   (- 36 (second thres))]
    ))


(comment
  (decode-gvli "kva")
  )


(defn basic?
  "Check if cp is a basic code point."
  [cp]
  (< (int cp) 128))


(defn delim?
  "Check if cp is a delimiter."
  [cp]
  (= (int cp) 0x2D))


(defn- encode-unicode-char [n]
  )


(comment
  (require 'uk.axvr.debug)

  ;; (defn encode-label [l]
  (uk.axvr.debug/quick-bench
    (loop [cs "bücher"
           ascii (transient [])
           unicode (transient [])]
      (if-let [c (first cs)]
        (let [ci (int c)]
          (if (< ci 128)
            (recur (rest cs)
                   (conj! ascii c)
                   unicode)
            (recur (rest cs)
                   ascii
                   (conj! unicode
                          {:idx (count ascii)
                           :dist (- ci 128)}))))
        (let [ascii (persistent! (if (pos? (count ascii))
                                   (conj! ascii \-)
                                   ascii))]
          [ascii (map-indexed
                   (fn [i v]
                     ;; TODO: convert this number to chars and concat all of them onto ascii.
                     (+ (* (+ (count ascii) i) (:dist v)) (:idx v)))
                   (persistent! unicode))]))))


  (uk.axvr.debug/quick-bench (java.net.IDN/toASCII "bücher"))
  )


;; (def ^:private ascii-charset
;;   (set (map char (range 0 128))))

;; (loop [i 0
;;        n 128]
;;   (if (= i (inc (count s)))
;;     (recur 0 (inc n))
;;     (recur (inc 0) n)))

;; (defn encode-label [s]
;;   (let [ascii-only (pull-ascii-chars s)]
;;     (if (seq ascii-only)
;;       ;; Append '-' to end.
;;       (str (apply str ascii-only) \-)
;;       (apply str ascii-only))))

;; (defn encode-label [s])

;; (defn encode [s])

;; (defn decode-label [s])
