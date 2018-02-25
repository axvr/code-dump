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

NeoBundleLazy 'tpope/vim-fugitive',               { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'scrooloose/nerdtree',              { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'scrooloose/syntastic',             { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'tpope/vim-surround',               { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'ctrlpvim/ctrlp.vim',               { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'altercation/vim-colors-solarized', { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'majutsushi/tagbar',                { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'scrooloose/nerdcommenter',         { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'airblade/vim-gitgutter',           { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'valloric/youcompleteme',           { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'vim-airline/vim-airline-themes',   { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'pangloss/vim-javascript',          { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'easymotion/vim-easymotion',        { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'vim-airline/vim-airline',          { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'godlygeek/tabular',                { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'fatih/vim-go',                     { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'honza/vim-snippets',               { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'ervandew/supertab',                { 'autoload': { 'filetypes': 'testing' } }
NeoBundleLazy 'kchmck/vim-coffee-script',         { 'autoload': { 'filetypes': 'testing' } }

call neobundle#end()

" Required:
filetype plugin indent on
syntax enable
