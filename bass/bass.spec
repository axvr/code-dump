Name:    bass
Summary: Bass is a scripting language for running commands and caching the shit out of them
Version: 0.9.0
Release: 1%{?dist}

License: MIT
Url:     https://bass-lang.org
Source0: https://github.com/vito/bass/releases/download/v%{version}/bass.linux-amd64.tgz
Source1: https://github.com/vito/bass/archive/refs/tags/v%{version}.tar.gz
Source2: https://github.com/vito/bass/releases/download/v%{version}/bass.linux-amd64.json

Requires: docker

BuildArch: x86_64

%description
Bass is a scripting language for running commands and caching the shit out of
them.

Bass's goal is to make shipping software predictable, repeatable, and fun. The
plan is to support sophisticated CI/CD flows while sticking to familiar
ideas. CI/CD boils down to running commands. Bass leverages that instead of
trying to replace it.

%prep
tar zxvof %{_sourcedir}/bass.linux-amd64.tgz
chmod -R a+rX,g-w,o-w .

%build

%install
mkdir -p %{buildroot}%{_bindir}
cp bass %{buildroot}%{_bindir}/

%files
%{_bindir}/bass

%changelog
