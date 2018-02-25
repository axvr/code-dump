set packpath=$TEST_DIR

packadd minpac
call minpac#init()

call minpac#add('tpope/vim-fugitive',               { 'type': 'opt' })
call minpac#add('scrooloose/nerdtree',              { 'type': 'opt' })
call minpac#add('scrooloose/syntastic',             { 'type': 'opt' })
call minpac#add('tpope/vim-surround',               { 'type': 'opt' })
call minpac#add('ctrlpvim/ctrlp.vim',               { 'type': 'opt' })
call minpac#add('altercation/vim-colors-solarized', { 'type': 'opt' })
call minpac#add('majutsushi/tagbar',                { 'type': 'opt' })
call minpac#add('scrooloose/nerdcommenter',         { 'type': 'opt' })
call minpac#add('airblade/vim-gitgutter',           { 'type': 'opt' })
call minpac#add('valloric/youcompleteme',           { 'type': 'opt' })
call minpac#add('vim-airline/vim-airline-themes',   { 'type': 'opt' })
call minpac#add('pangloss/vim-javascript',          { 'type': 'opt' })
call minpac#add('easymotion/vim-easymotion',        { 'type': 'opt' })
call minpac#add('vim-airline/vim-airline',          { 'type': 'opt' })
call minpac#add('godlygeek/tabular',                { 'type': 'opt' })
call minpac#add('fatih/vim-go',                     { 'type': 'opt' })
call minpac#add('honza/vim-snippets',               { 'type': 'opt' })
call minpac#add('ervandew/supertab',                { 'type': 'opt' })
call minpac#add('kchmck/vim-coffee-script',         { 'type': 'opt' })

