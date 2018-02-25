set runtimepath^=$TEST_DIR

call plug#begin('$TEST_DIR/bundle')

Plug 'tpope/vim-fugitive',               { 'for': 'testing' }
Plug 'scrooloose/nerdtree',              { 'for': 'testing' }
Plug 'scrooloose/syntastic',             { 'for': 'testing' }
Plug 'tpope/vim-surround',               { 'for': 'testing' }
Plug 'ctrlpvim/ctrlp.vim',               { 'for': 'testing' }
Plug 'altercation/vim-colors-solarized', { 'for': 'testing' }
Plug 'majutsushi/tagbar',                { 'for': 'testing' }
Plug 'scrooloose/nerdcommenter',         { 'for': 'testing' }
Plug 'airblade/vim-gitgutter',           { 'for': 'testing' }
Plug 'valloric/youcompleteme',           { 'for': 'testing' }
Plug 'vim-airline/vim-airline-themes',   { 'for': 'testing' }
Plug 'pangloss/vim-javascript',          { 'for': 'testing' }
Plug 'easymotion/vim-easymotion',        { 'for': 'testing' }
Plug 'vim-airline/vim-airline',          { 'for': 'testing' }
Plug 'godlygeek/tabular',                { 'for': 'testing' }
Plug 'fatih/vim-go',                     { 'for': 'testing' }
Plug 'honza/vim-snippets',               { 'for': 'testing' }
Plug 'ervandew/supertab',                { 'for': 'testing' }
Plug 'kchmck/vim-coffee-script',         { 'for': 'testing' }

call plug#end()
" plug#end() does `filetype plugin indent on` and `syntax enable`
