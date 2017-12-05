set packpath+=$TEST_DIR

packadd minpac
call minpac#init()

" My plugins
call minpac#add('junegunn/vim-easy-align',          { 'type': 'opt', })
call minpac#add('junegunn/vim-github-dashboard',    { 'type': 'opt', })
call minpac#add('junegunn/vim-emoji',               { 'type': 'opt', })
call minpac#add('junegunn/vim-pseudocl',            { 'type': 'opt', })
call minpac#add('junegunn/vim-slash',               { 'type': 'opt', })
call minpac#add('junegunn/vim-fnr',                 { 'type': 'opt', })
call minpac#add('junegunn/vim-peekaboo',            { 'type': 'opt', })
call minpac#add('junegunn/vim-journal',             { 'type': 'opt', })
call minpac#add('junegunn/seoul256.vim',            { 'type': 'opt', })
call minpac#add('junegunn/gv.vim',                  { 'type': 'opt', })
call minpac#add('junegunn/goyo.vim',                { 'type': 'opt', })
call minpac#add('junegunn/limelight.vim',           { 'type': 'opt', })
call minpac#add('junegunn/vader.vim',               { 'type': 'opt', })
call minpac#add('junegunn/vim-ruby-x',              { 'type': 'opt', })
call minpac#add('junegunn/fzf',                     { 'type': 'opt', })
call minpac#add('junegunn/fzf.vim',                 { 'type': 'opt', })
call minpac#add('junegunn/rainbow_parentheses.vim', { 'type': 'opt', })
call minpac#add('junegunn/vim-after-object',        { 'type': 'opt', })
call minpac#add('junegunn/vim-xmark',               { 'type': 'opt', })

" Colors
call minpac#add('tomasr/molokai',                   { 'type': 'opt', })
call minpac#add('chriskempson/vim-tomorrow-theme',  { 'type': 'opt', })

" Edit
call minpac#add('tpope/vim-repeat',                 { 'type': 'opt', })
call minpac#add('tpope/vim-surround',               { 'type': 'opt', })
call minpac#add('tpope/vim-endwise',                { 'type': 'opt', })
call minpac#add('tpope/vim-commentary',             { 'type': 'opt', })
call minpac#add('mbbill/undotree',                  { 'type': 'opt', })
call minpac#add('vim-scripts/ReplaceWithRegister',  { 'type': 'opt', })
call minpac#add('AndrewRadev/splitjoin.vim',        { 'type': 'opt', })
call minpac#add('rhysd/vim-grammarous',             { 'type': 'opt', })
call minpac#add('junegunn/vim-online-thesaurus',    { 'type': 'opt', })

" Tmux
call minpac#add('tpope/vim-tbone',                  { 'type': 'opt', })

" Browsing
call minpac#add('Yggdroot/indentLine',              { 'type': 'opt', })
call minpac#add('scrooloose/nerdtree',              { 'type': 'opt', })

call minpac#add('majutsushi/tagbar',                { 'type': 'opt', })
call minpac#add('justinmk/vim-gtfo',                { 'type': 'opt', })

" Git
call minpac#add('tpope/vim-fugitive',               { 'type': 'opt', })
call minpac#add('mhinz/vim-signify',                { 'type': 'opt', })

" Lang
call minpac#add('vim-ruby/vim-ruby',                { 'type': 'opt', })
call minpac#add('kovisoft/paredit',                 { 'type': 'opt', })
call minpac#add('tpope/vim-fireplace',              { 'type': 'opt', })
call minpac#add('guns/vim-clojure-static',          { 'type': 'opt', })
call minpac#add('guns/vim-clojure-highlight',       { 'type': 'opt', })
call minpac#add('guns/vim-slamhound',               { 'type': 'opt', })
call minpac#add('fatih/vim-go',                     { 'type': 'opt', })
call minpac#add('groenewege/vim-less',              { 'type': 'opt', })
call minpac#add('pangloss/vim-javascript',          { 'type': 'opt', })
call minpac#add('mxw/vim-jsx',                      { 'type': 'opt', })
call minpac#add('kchmck/vim-coffee-script',         { 'type': 'opt', })
call minpac#add('slim-template/vim-slim',           { 'type': 'opt', })
call minpac#add('Glench/Vim-Jinja2-Syntax',         { 'type': 'opt', })
call minpac#add('rust-lang/rust.vim',               { 'type': 'opt', })
call minpac#add('tpope/vim-rails',                  { 'type': 'opt', })
call minpac#add('derekwyatt/vim-scala',             { 'type': 'opt', })
call minpac#add('ensime/ensime-vim',                { 'type': 'opt', })
call minpac#add('honza/dockerfile.vim',             { 'type': 'opt', })
call minpac#add('solarnz/thrift.vim',               { 'type': 'opt', })
call minpac#add('dag/vim-fish',                     { 'type': 'opt', })
call minpac#add('chrisbra/unicode.vim',             { 'type': 'opt', })

" Lint
call minpac#add('scrooloose/syntastic',             { 'type': 'opt', })

