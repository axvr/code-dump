set packpath=$TEST_DIR/package
set packpath+=$TEST_DIR

packadd minpac
call minpac#init()

call minpac#add('tpope/vim-fugitive')
call minpac#add('scrooloose/nerdtree')
call minpac#add('scrooloose/syntastic')
call minpac#add('tpope/vim-surround')
call minpac#add('ctrlpvim/ctrlp.vim')
call minpac#add('altercation/vim-colors-solarized')
call minpac#add('majutsushi/tagbar')
call minpac#add('scrooloose/nerdcommenter')
call minpac#add('airblade/vim-gitgutter')
call minpac#add('valloric/youcompleteme')
call minpac#add('vim-airline/vim-airline-themes')
call minpac#add('pangloss/vim-javascript')
call minpac#add('easymotion/vim-easymotion')
call minpac#add('vim-airline/vim-airline')
call minpac#add('godlygeek/tabular')
call minpac#add('fatih/vim-go')
call minpac#add('honza/vim-snippets')
call minpac#add('ervandew/supertab')
call minpac#add('kchmck/vim-coffee-script')

