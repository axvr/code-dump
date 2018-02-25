set runtimepath^=$TEST_DIR

filetype off

set rtp+=$TEST_DIR/bundle/Vundle.vim
call vundle#begin()

Plugin 'tpope/vim-fugitive'
Plugin 'scrooloose/nerdtree'
Plugin 'scrooloose/syntastic'
Plugin 'tpope/vim-surround'
Plugin 'ctrlpvim/ctrlp.vim'
Plugin 'altercation/vim-colors-solarized'
Plugin 'majutsushi/tagbar'
Plugin 'scrooloose/nerdcommenter'
Plugin 'airblade/vim-gitgutter'
Plugin 'valloric/youcompleteme'
Plugin 'vim-airline/vim-airline-themes'
Plugin 'pangloss/vim-javascript'
Plugin 'easymotion/vim-easymotion'
Plugin 'vim-airline/vim-airline'
Plugin 'godlygeek/tabular'
Plugin 'fatih/vim-go'
Plugin 'honza/vim-snippets'
Plugin 'ervandew/supertab'
Plugin 'kchmck/vim-coffee-script'

call vundle#end()
filetype plugin indent on
syntax enable
