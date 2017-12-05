set runtimepath^=$TEST_DIR

call plug#begin('$TEST_DIR/bundle')

" My plugins
Plug 'junegunn/vim-easy-align',       { 'on': ['<Plug>(EasyAlign)', 'EasyAlign'] }
Plug 'junegunn/vim-github-dashboard', { 'on': ['GHDashboard', 'GHActivity']      }
Plug 'junegunn/vim-emoji', { 'for': 'stuff' }
Plug 'junegunn/vim-pseudocl', { 'for': 'stuff' }
Plug 'junegunn/vim-slash', { 'for': 'stuff' }
Plug 'junegunn/vim-fnr', { 'for': 'stuff' }
Plug 'junegunn/vim-peekaboo', { 'for': 'stuff' }
Plug 'junegunn/vim-journal', { 'for': 'stuff' }
Plug 'junegunn/seoul256.vim', { 'for': 'stuff' }
Plug 'junegunn/gv.vim', { 'for': 'stuff' }
Plug 'junegunn/goyo.vim', { 'for': 'stuff' }
Plug 'junegunn/limelight.vim', { 'for': 'stuff' }
Plug 'junegunn/vader.vim',  { 'on': 'Vader', 'for': 'vader' }
Plug 'junegunn/vim-ruby-x', { 'on': 'RubyX' }
Plug 'junegunn/fzf', { 'for': 'stuff' }
Plug 'junegunn/fzf.vim', { 'for': 'stuff' }
Plug 'junegunn/rainbow_parentheses.vim', { 'for': 'stuff' }
Plug 'junegunn/vim-after-object', { 'for': 'stuff' }
Plug 'junegunn/vim-xmark', { 'for': 'stuff' }

" Colors
Plug 'tomasr/molokai', { 'for': 'stuff' }
Plug 'chriskempson/vim-tomorrow-theme', { 'for': 'stuff' }

" Edit
Plug 'tpope/vim-repeat', { 'for': 'stuff' }
Plug 'tpope/vim-surround', { 'for': 'stuff' }
Plug 'tpope/vim-endwise', { 'for': 'stuff' }
Plug 'tpope/vim-commentary',        { 'on': '<Plug>Commentary' }
Plug 'mbbill/undotree',             { 'on': 'UndotreeToggle'   }
Plug 'vim-scripts/ReplaceWithRegister', { 'for': 'stuff' }
Plug 'AndrewRadev/splitjoin.vim', { 'for': 'stuff' }
Plug 'rhysd/vim-grammarous', { 'for': 'stuff' }
Plug 'junegunn/vim-online-thesaurus', { 'for': 'stuff' }

" Tmux
Plug 'tpope/vim-tbone', { 'for': 'stuff' }

" Browsing
Plug 'Yggdroot/indentLine', { 'on': 'IndentLinesEnable' }
Plug 'scrooloose/nerdtree', { 'on': 'NERDTreeToggle' }

Plug 'majutsushi/tagbar',   { 'on': 'TagbarToggle' }
Plug 'justinmk/vim-gtfo', { 'for': 'stuff' }

" Git
Plug 'tpope/vim-fugitive', { 'for': 'stuff' }
Plug 'mhinz/vim-signify', { 'for': 'stuff' }

" Lang
Plug 'vim-ruby/vim-ruby', { 'for': 'stuff' }
Plug 'kovisoft/paredit',    { 'for': 'clojure' }
Plug 'tpope/vim-fireplace', { 'for': 'clojure' }
Plug 'guns/vim-clojure-static', { 'for': 'stuff' }
Plug 'guns/vim-clojure-highlight', { 'for': 'stuff' }
Plug 'guns/vim-slamhound', { 'for': 'stuff' }
Plug 'fatih/vim-go', { 'for': 'stuff' }
Plug 'groenewege/vim-less', { 'for': 'stuff' }
Plug 'pangloss/vim-javascript', { 'for': 'stuff' }
Plug 'mxw/vim-jsx', { 'for': 'stuff' }
Plug 'kchmck/vim-coffee-script', { 'for': 'stuff' }
Plug 'slim-template/vim-slim', { 'for': 'stuff' }
Plug 'Glench/Vim-Jinja2-Syntax', { 'for': 'stuff' }
Plug 'rust-lang/rust.vim', { 'for': 'stuff' }
Plug 'tpope/vim-rails',      { 'for': 'ruby'      }
Plug 'derekwyatt/vim-scala', { 'for': 'stuff' }
Plug 'ensime/ensime-vim',    { 'for': 'scala' }
Plug 'honza/dockerfile.vim', { 'for': 'stuff' }
Plug 'solarnz/thrift.vim', { 'for': 'stuff' }
Plug 'dag/vim-fish', { 'for': 'stuff' }
Plug 'chrisbra/unicode.vim', { 'for': 'journal' }

" Lint
Plug 'scrooloose/syntastic', { 'on': 'SyntasticCheck' }

call plug#end()
" plug#end() does `filetype plugin indent on` and `syntax enable`
