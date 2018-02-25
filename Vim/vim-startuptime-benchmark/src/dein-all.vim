set runtimepath^=$TEST_DIR

set runtimepath+=$TEST_DIR/dein.vim
call dein#begin($TEST_DIR.'/dein-all')

call dein#add('tpope/vim-fugitive')
call dein#add('scrooloose/nerdtree')
call dein#add('scrooloose/syntastic')
call dein#add('tpope/vim-surround')
call dein#add('ctrlpvim/ctrlp.vim')
call dein#add('altercation/vim-colors-solarized')
call dein#add('majutsushi/tagbar')
call dein#add('scrooloose/nerdcommenter')
call dein#add('airblade/vim-gitgutter')
call dein#add('valloric/youcompleteme')
call dein#add('vim-airline/vim-airline-themes')
call dein#add('pangloss/vim-javascript')
call dein#add('easymotion/vim-easymotion')
call dein#add('vim-airline/vim-airline')
call dein#add('godlygeek/tabular')
call dein#add('fatih/vim-go')
call dein#add('honza/vim-snippets')
call dein#add('ervandew/supertab')
call dein#add('kchmck/vim-coffee-script')

call dein#end()

filetype plugin indent on
syntax enable
