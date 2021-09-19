FROM clojure:tools-deps
WORKDIR /primes
COPY deps.edn primes.clj ./
ENTRYPOINT ["clojure", "-X:run"]
