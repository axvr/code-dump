set runtimepath^=$TEST_DIR

set runtimepath+=$TEST_DIR/dein.vim
call dein#begin($TEST_DIR.'/dein')

call dein#add('tpope/vim-fugitive',               { 'on_ft': 'testing' })
call dein#add('scrooloose/nerdtree',              { 'on_ft': 'testing' })
call dein#add('scrooloose/syntastic',             { 'on_ft': 'testing' })
call dein#add('tpope/vim-surround',               { 'on_ft': 'testing' })
call dein#add('ctrlpvim/ctrlp.vim',               { 'on_ft': 'testing' })
call dein#add('altercation/vim-colors-solarized', { 'on_ft': 'testing' })
call dein#add('majutsushi/tagbar',                { 'on_ft': 'testing' })
call dein#add('scrooloose/nerdcommenter',         { 'on_ft': 'testing' })
call dein#add('airblade/vim-gitgutter',           { 'on_ft': 'testing' })
call dein#add('valloric/youcompleteme',           { 'on_ft': 'testing' })
call dein#add('vim-airline/vim-airline-themes',   { 'on_ft': 'testing' })
call dein#add('pangloss/vim-javascript',          { 'on_ft': 'testing' })
call dein#add('easymotion/vim-easymotion',        { 'on_ft': 'testing' })
call dein#add('vim-airline/vim-airline',          { 'on_ft': 'testing' })
call dein#add('godlygeek/tabular',                { 'on_ft': 'testing' })
call dein#add('fatih/vim-go',                     { 'on_ft': 'testing' })
call dein#add('honza/vim-snippets',               { 'on_ft': 'testing' })
call dein#add('ervandew/supertab',                { 'on_ft': 'testing' })
call dein#add('kchmck/vim-coffee-script',         { 'on_ft': 'testing' })

call dein#end()

filetype plugin indent on
syntax enable
