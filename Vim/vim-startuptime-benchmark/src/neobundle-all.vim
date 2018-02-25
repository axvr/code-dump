set runtimepath^=$TEST_DIR

if has('vim_starting')
  " Required:
  set runtimepath+=$TEST_DIR/bundle/neobundle.vim/
endif

" Required:
call neobundle#begin(expand('$TEST_DIR/bundle'))

" Let NeoBundle manage NeoBundle
" Required:
NeoBundleFetch 'Shougo/neobundle.vim'

NeoBundle 'tpope/vim-fugitive'
NeoBundle 'scrooloose/nerdtree'
NeoBundle 'scrooloose/syntastic'
NeoBundle 'tpope/vim-surround'
NeoBundle 'ctrlpvim/ctrlp.vim'
NeoBundle 'altercation/vim-colors-solarized'
NeoBundle 'majutsushi/tagbar'
NeoBundle 'scrooloose/nerdcommenter'
NeoBundle 'airblade/vim-gitgutter'
NeoBundle 'valloric/youcompleteme'
NeoBundle 'vim-airline/vim-airline-themes'
NeoBundle 'pangloss/vim-javascript'
NeoBundle 'easymotion/vim-easymotion'
NeoBundle 'vim-airline/vim-airline'
NeoBundle 'godlygeek/tabular'
NeoBundle 'fatih/vim-go'
NeoBundle 'honza/vim-snippets'
NeoBundle 'ervandew/supertab'
NeoBundle 'kchmck/vim-coffee-script'

call neobundle#end()

" Required:
filetype plugin indent on
syntax enable
