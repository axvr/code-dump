Name:    clojure
Summary: Clojure programming language command line tools
Version: 1.11.1.1155
Release: 1%{?dist}

License: EPL-1.0
Url:     https://clojure.org
Source0: https://download.clojure.org/install/clojure-tools-%{version}.tar.gz

Requires: rlwrap

BuildArch: noarch

%description
Clojure is a dynamic, general-purpose programming language, combining the
approachability and interactive development of a scripting language with an
efficient and robust infrastructure for multithreaded programming.  Clojure is
a compiled language, yet remains completely dynamic – every feature supported
by Clojure is supported at runtime.  Clojure provides easy access to the Java
frameworks, with optional type hints and type inference, to ensure that calls
to Java can avoid reflection.

Clojure is a dialect of Lisp, and shares with Lisp the code-as-data philosophy
and a powerful macro system.  Clojure is predominantly a functional programming
language, and features a rich set of immutable, persistent data structures.
When mutable state is needed, Clojure offers a software transactional memory
system and reactive Agent system that ensure clean, correct, multithreaded
designs.

%prep
tar zxvof %{_sourcedir}/clojure-tools-%{version}.tar.gz
chmod -R a+rX,g-w,o-w .

%build

%install
mkdir -p %{buildroot}%{_libdir}/clojure/libexec
mkdir -p %{buildroot}%{_mandir}/man1
mkdir -p %{buildroot}%{_bindir}
cd clojure-tools
sed -i -e 's,BINDIR,%{_bindir},g' clj
sed -i -e 's,PREFIX,%{_libdir}/clojure,g' clojure
cp {clj,clojure} %{buildroot}%{_bindir}/
cp deps.edn example-deps.edn tools.edn %{buildroot}%{_libdir}/clojure
cp *.jar %{buildroot}%{_libdir}/clojure/libexec
cp *.1 %{buildroot}%{_mandir}/man1

%files
%{_bindir}/clj
%{_bindir}/clojure
%{_mandir}/man1/clj.1*
%{_mandir}/man1/clojure.1*
%dir %{_libdir}/clojure/
%dir %{_libdir}/clojure/libexec/
%{_libdir}/clojure/*
%{_libdir}/clojure/libexec/*

%changelog
