set packpath=$TEST_DIR/package
set packpath+=$TEST_DIR

packadd minpac
call minpac#init()

" My plugins
call minpac#add('junegunn/vim-easy-align')
call minpac#add('junegunn/vim-github-dashboard')
call minpac#add('junegunn/vim-emoji')
call minpac#add('junegunn/vim-pseudocl')
call minpac#add('junegunn/vim-slash')
call minpac#add('junegunn/vim-fnr')
call minpac#add('junegunn/vim-peekaboo')
call minpac#add('junegunn/vim-journal')
call minpac#add('junegunn/seoul256.vim')
call minpac#add('junegunn/gv.vim')
call minpac#add('junegunn/goyo.vim')
call minpac#add('junegunn/limelight.vim')
call minpac#add('junegunn/vader.vim')
call minpac#add('junegunn/vim-ruby-x')
call minpac#add('junegunn/fzf')
call minpac#add('junegunn/fzf.vim')
call minpac#add('junegunn/rainbow_parentheses.vim')
call minpac#add('junegunn/vim-after-object')
call minpac#add('junegunn/vim-xmark')

" Colors
call minpac#add('tomasr/molokai')
call minpac#add('chriskempson/vim-tomorrow-theme')

" Edit
call minpac#add('tpope/vim-repeat')
call minpac#add('tpope/vim-surround')
call minpac#add('tpope/vim-endwise')
call minpac#add('tpope/vim-commentary')
call minpac#add('mbbill/undotree')
call minpac#add('vim-scripts/ReplaceWithRegister')
call minpac#add('AndrewRadev/splitjoin.vim')
call minpac#add('rhysd/vim-grammarous')
call minpac#add('junegunn/vim-online-thesaurus')

" Tmux
call minpac#add('tpope/vim-tbone')

" Browsing
call minpac#add('Yggdroot/indentLine')
call minpac#add('scrooloose/nerdtree')

call minpac#add('majutsushi/tagbar')
call minpac#add('justinmk/vim-gtfo')

" Git
call minpac#add('tpope/vim-fugitive')
call minpac#add('mhinz/vim-signify')

" Lang
call minpac#add('vim-ruby/vim-ruby')
call minpac#add('kovisoft/paredit')
call minpac#add('tpope/vim-fireplace')
call minpac#add('guns/vim-clojure-static')
call minpac#add('guns/vim-clojure-highlight')
call minpac#add('guns/vim-slamhound')
call minpac#add('fatih/vim-go')
call minpac#add('groenewege/vim-less')
call minpac#add('pangloss/vim-javascript')
call minpac#add('mxw/vim-jsx')
call minpac#add('kchmck/vim-coffee-script')
call minpac#add('slim-template/vim-slim')
call minpac#add('Glench/Vim-Jinja2-Syntax')
call minpac#add('rust-lang/rust.vim')
call minpac#add('tpope/vim-rails')
call minpac#add('derekwyatt/vim-scala')
call minpac#add('ensime/ensime-vim')
call minpac#add('honza/dockerfile.vim')
call minpac#add('solarnz/thrift.vim')
call minpac#add('dag/vim-fish')
call minpac#add('chrisbra/unicode.vim')

" Lint
call minpac#add('scrooloose/syntastic')

