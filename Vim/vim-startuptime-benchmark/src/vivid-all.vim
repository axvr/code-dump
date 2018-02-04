set packpath=$TEST_DIR

packadd Vivid.vim

" My plugins
call vivid#add('junegunn/vim-easy-align')
call vivid#add('junegunn/vim-github-dashboard')
call vivid#add('junegunn/vim-emoji')
call vivid#add('junegunn/vim-pseudocl')
call vivid#add('junegunn/vim-slash')
call vivid#add('junegunn/vim-fnr')
call vivid#add('junegunn/vim-peekaboo')
call vivid#add('junegunn/vim-journal')
call vivid#add('junegunn/seoul256.vim')
call vivid#add('junegunn/gv.vim')
call vivid#add('junegunn/goyo.vim')
call vivid#add('junegunn/limelight.vim')
call vivid#add('junegunn/vader.vim')
call vivid#add('junegunn/vim-ruby-x')
call vivid#add('junegunn/fzf')
call vivid#add('junegunn/fzf.vim')
call vivid#add('junegunn/rainbow_parentheses.vim')
call vivid#add('junegunn/vim-after-object')
call vivid#add('junegunn/vim-xmark')

" Colors
call vivid#add('tomasr/molokai')
call vivid#add('chriskempson/vim-tomorrow-theme')

" Edit
call vivid#add('tpope/vim-repeat')
call vivid#add('tpope/vim-surround')
call vivid#add('tpope/vim-endwise')
call vivid#add('tpope/vim-commentary')
call vivid#add('mbbill/undotree')
call vivid#add('vim-scripts/ReplaceWithRegister')
call vivid#add('AndrewRadev/splitjoin.vim')
call vivid#add('rhysd/vim-grammarous')
call vivid#add('junegunn/vim-online-thesaurus')

" Tmux
call vivid#add('tpope/vim-tbone')

" Browsing
call vivid#add('Yggdroot/indentLine')
call vivid#add('scrooloose/nerdtree')

call vivid#add('majutsushi/tagbar')
call vivid#add('justinmk/vim-gtfo')

" Git
call vivid#add('tpope/vim-fugitive')
call vivid#add('mhinz/vim-signify')

" Lang
call vivid#add('vim-ruby/vim-ruby')
call vivid#add('kovisoft/paredit')
call vivid#add('tpope/vim-fireplace')
call vivid#add('guns/vim-clojure-static')
call vivid#add('guns/vim-clojure-highlight')
call vivid#add('guns/vim-slamhound')
call vivid#add('fatih/vim-go')
call vivid#add('groenewege/vim-less')
call vivid#add('pangloss/vim-javascript')
call vivid#add('mxw/vim-jsx')
call vivid#add('kchmck/vim-coffee-script')
call vivid#add('slim-template/vim-slim')
call vivid#add('Glench/Vim-Jinja2-Syntax')
call vivid#add('rust-lang/rust.vim')
call vivid#add('tpope/vim-rails')
call vivid#add('derekwyatt/vim-scala')
call vivid#add('ensime/ensime-vim')
call vivid#add('honza/dockerfile.vim')
call vivid#add('solarnz/thrift.vim')
call vivid#add('dag/vim-fish')
call vivid#add('chrisbra/unicode.vim')

" Lint
call vivid#add('scrooloose/syntastic')

call vivid#enable()
" plug#end() does `filetype plugin indent on` and `syntax enable`
