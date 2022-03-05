(ns qr
  (:import [java.io File]
           [com.google.zxing BarcodeFormat MultiFormatWriter]
           [com.google.zxing.client.j2se MatrixToImageWriter]))


(defn generate [content path size]
  (let [matrix (.encode (MultiFormatWriter.)
                        content
                        BarcodeFormat/QR_CODE
                        size
                        size)]
    (MatrixToImageWriter/writeToFile
      matrix
      (subs path (inc (.lastIndexOf path ".")))
      (File. path))))


(comment
  (generate "axvr.uk" "qr-axvr.png" 2500)
  (generate "https://axvr.uk" "qr-axvr-https.png" 2500)
  )
