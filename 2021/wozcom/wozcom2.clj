(ns wozcom
  (:require [uk.axvr.cereal :as serial]
            [clojure.string :as str]))


;;; Public domain.  No rights reserved.


(extend-protocol serial/Bytable
  String
  (to-bytes [this] (.getBytes this "ASCII")))


(defn listener [stream]
  (print (char (.read stream)))
  (flush))


(defn format-instruction [line]
  (str (str/replace line #"\s+" " ") "\r"))


(defn format-prog [prog]
  (map (comp serial/to-bytes format-instruction) prog))


(with-open [port (serial/open "ttyUSB0" :baud-rate 9600 :listen listener)]
  (loop [input ""]
    (when (and input (not= input "quit"))
      (doseq [c (format-prog [input])]
        (Thread/sleep 70)
        (serial/send! port c))
      (recur (read-line)))))


(comment
  (defn load-prog [prog]
    (doseq [i (format-prog prog)]
      (Thread/sleep 70)
      (serial/send! port i)))

  (def prog
    ["N"
     "     LDA #'A'"
     "LOOP JSR $FFEF"
     "     CLC"
     "     ADC #$1"
     "     CMP #'Z'+1"
     "     BNE LOOP"
     "     RTS"
     "\u001BA"])

  (load-prog ["F000 R"])
  (load-prog prog)
  (load-prog ["R $300"])


  ;; TODO: wait while printing.

  (def prog2
    ["N"
     "ECHO   .=  $FFEF"
     "START  .=  'A'"
     "END    .=  'Z'"
     "STEP   .=  $30"
     ""
     "SETUP  .M  $280"
     "       LDA #$1"
     "       STA STEP"
     "       LDA #START"
     "       RTS"
     ""
     "FWD    .M  $300"
     ".LOOP  JSR ECHO"
     "       CLC"
     "       ADC STEP"
     "       CMP #END"
     "       BMI .LOOP"
     "       RTS"
     ""
     "BACK   .M  $320"
     ".LOOP  JSR ECHO"
     "       SEC"
     "       SBC STEP"
     "       CMP #START"
     "       BPL .LOOP"
     "       RTS"
     ""
     "MAIN   .M  $340"
     "       JSR SETUP"
     "       JSR FWD"
     "       JSR BACK"
     "       RTS"
     "\u001BA"])

  (load-prog ["F000 R"])
  (load-prog prog2)
  (load-prog ["R MAIN"])


  (def prog3
    ["10 FOR I = 1 TO 20"
     "20 PRINT \"HELLO \" I"
     "30 NEXT I"
     "40 END"])

  (load-prog ["E000 R"])
  (load-prog prog3)
  (load-prog ["LIST"])
  (load-prog ["RUN"])
  )
