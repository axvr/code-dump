" Org.vim[Fn0] extension to make folding closer to Org mode in GNU Emacs by
" defining the "Tab" and "Shift-Tab" key bindings.
"
" Path:   ~/.vim/after/ftplugin/org.vim or ~/.config/nvim/after/ftplugin/org.vim
" Author: Alex Vear
" Date:   2021-03-23
" Legal:  No rights reserved.  Public domain.
" Fn0:    <https://github.com/axvr/org.vim>

let b:org_clean_folds = 1

setlocal foldenable
normal zv

function! s:org_tab_cycle() abort
    let next_state = get(b:, 'org_folding_tab_next_state', 'FOLDED')

    if next_state ==# 'CHILDREN'
        normal zo
        let b:org_folding_tab_next_state = 'SUBTREE'
    elseif next_state ==# 'SUBTREE'
        normal zc
        silent .foldopen!
        let b:org_folding_tab_next_state = 'FOLDED'
    elseif next_state ==# 'FOLDED'
        normal zozc
        silent .foldclose!
        normal zvzc
        let b:org_folding_tab_next_state = 'CHILDREN'
    else
        unlet! b:org_folding_tab_next_state
    endif

    echo next_state
endfunction

function! s:org_shift_tab_cycle() abort
    " NOTE: 'CONTENTS'-like behaviour is not supported by Vim.

    let next_state = get(b:, 'org_folding_shift_tab_next_state', 'OVERVIEW')

    if next_state ==# 'OVERVIEW'
        normal zM
        let b:org_folding_shift_tab_next_state = 'SHOW ALL'
    elseif next_state ==# 'SHOW ALL'
        normal zR
        let b:org_folding_shift_tab_next_state = 'OVERVIEW'
    else
        unlet! b:org_folding_shift_tab_next_state
    endif

    echo next_state
endfunction

nnoremap <buffer> <Tab> :call <SID>org_tab_cycle()<CR>
nnoremap <buffer> <S-Tab> :call <SID>org_shift_tab_cycle()<CR>

augroup OrgFold
  autocmd!
  autocmd CursorMoved <buffer> unlet! b:org_folding_tab_next_state
                                    \ b:org_folding_shift_tab_next_state
augroup END
