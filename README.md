# Code Dump

A repository of random code, programming notes and project assets I have
created, ranging from experiments to fully functioning programs.

Feel free to use or modify any of this code where ever and how ever you like
(within the scope of the licence). Everything here is unlicenced and in the
public domain, unless otherwise stated.
 

---


### Some of the fully functional programs

* **[OmniSharp-Manager]** - A simple shell script to simplify the process of
  installing and updating OmniSharp. It has since been moved to
  [OmniSharp-vim](https://github.com/OmniSharp/omnisharp-vim) and expanded. I
  also wrote a version of it in PowerShell, shortly after the Unix-only version
  was merged into upstream OmniSharp-vim.
* **[Local-NuPkg]** - A small shell script to simplify the process of installing
  downloaded [NuGet](https://www.nuget.org) packages for .NET Core, without
  having to use/configure a feed. This is a feature missing from the
  [`dotnet-cli`](https://github.com/dotnet/cli) (v2.0).
* **[GNOME Theme Switcher]** - A simple bash script to allow modifying a large
  number of GNOME settings at once, esentially creating "complete themes". For
  more info [see here](https://www.reddit.com/r/unixporn/comments/73l9qg/gnome_script_to_switch_gnome_themes/).


### Some incomplete/deprecated programs

* **[ALIS]** - [Arch Linux](https://archlinux.org) Installation Script. Was
  going to be renamed to Archaic - The Primitive [Arch Linux] Installer, but I
  abandoned the project in late 2017 to focus on other tasks. ALIS had reached
  in insane level of code complexity, that a second rewrite was required to
  keep it maintainable (I was very new to programming at the time). A full
  explanation can be found in the project's
  [`README.md`](https://github.com/axvr/ALIS/blob/master/README.md).
* **[Vivid-Legacy.vim]** - The code from before the
  [rewrite](https://github.com/axvr/vivid.vim). This version was a fork of
  [Vundle.vim](https://github.com/VundleVim/Vundle.vim).


[OmniSharp-Manager]:https://github.com/OmniSharp/omnisharp-vim/tree/master/installer
[Vivid-Legacy.vim]:https://github.com/axvr/Vivid-Legacy.vim
[ALIS]:https://github.com/axvr/ALIS
[GNOME Theme Switcher]:https://github.com/axvr/dotfiles/blob/master/manage/gnome-theme.sh
[Local-NuPkg]:https://github.com/axvr/codedump/blob/master/Shell/local-nupkg.sh
