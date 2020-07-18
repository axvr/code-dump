" Indent the s-expr the cursor is currently in.
" Created early 2020 by Alex Vear.
" Public domain.

function! s:indent_sexpr() abort
    let pos = getcurpos()
    let char = matchstr(getline('.'), '\%' . col('.') . 'c.')
    silent! exec 'normal! ' . (char ==# '(' ? '=ab' : "[(=ab")
    call setpos('.', pos)
endfunction

nnoremap <buffer> <silent> == :<C-u>call <SID>indent_sexpr()<CR>
