(ns wozcom
  (:require [uk.axvr.cereal :as serial]
            [clojure.string :as str]))


;;; Public domain.  No rights reserved.


(serial/list-ports)

(def port (serial/open "ttyUSB0" :baud-rate 9600))



(with-open [port (serial/open "ttyUSB0" :baud-rate 9600)]
  (serial/listen! port (fn [stream]
                         (print (char (.read stream)))
                         (flush)))
  (loop [input ""]
    (when (and input (not= input "quit"))
      (doseq [c (format-prog [input])]
        (Thread/sleep 70)
        (serial/write! port c))
      (recur (.read *in*)))))

(serial/unlisten! port)

(serial/listen!
  port
  (fn [in-stream]
    (print (char (.read in-stream)))
    (flush)))


(defn format-instruction [line]
  (str (str/replace line #"\s+" " ") "\r"))

(defn format-prog [prog]
  (->> prog
       (map format-instruction)
       (mapcat #(. % getBytes "ASCII"))))

(defn load-prog [prog]
  (doseq [i (format-prog prog)]
    (Thread/sleep 70)
    (serial/write! port i)))

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

(serial/close! port)



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
