set runtimepath^=$TEST_DIR

call plug#begin('$TEST_DIR/bundle')

Plug 'tpope/vim-fugitive'
Plug 'scrooloose/nerdtree'
Plug 'scrooloose/syntastic'
Plug 'tpope/vim-surround'
Plug 'ctrlpvim/ctrlp.vim'
Plug 'altercation/vim-colors-solarized'
Plug 'majutsushi/tagbar'
Plug 'scrooloose/nerdcommenter'
Plug 'airblade/vim-gitgutter'
Plug 'valloric/youcompleteme'
Plug 'vim-airline/vim-airline-themes'
Plug 'pangloss/vim-javascript'
Plug 'easymotion/vim-easymotion'
Plug 'vim-airline/vim-airline'
Plug 'godlygeek/tabular'
Plug 'fatih/vim-go'
Plug 'honza/vim-snippets'
Plug 'ervandew/supertab'
Plug 'kchmck/vim-coffee-script'

call plug#end()
" plug#end() does `filetype plugin indent on` and `syntax enable`
