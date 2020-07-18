" Functions to temporarily set a variable, do an action, then revert the
" variable change.

" Written in early 2019 by Alex Vear.
" Public domain.  No rights reserved.

com! -nargs=* -complete=file Todos call Tlsc('&gp', 'todos', {-> execute("gr <args>", 1)})
com! -nargs=+ -complete=file GitGrep call Tlsc('&gp', 'git grep -n', {-> execute("gr <args>", 1)})

" Temporary state container for buffer local variables and options
function Tlsc(var,val,op) abort
    let l:buf=bufnr('%')
    let l:prev=getbufvar(l:buf,a:var)
    call setbufvar(l:buf,a:var,a:val)
    call a:op()
    call setbufvar(l:buf,a:var,l:prev)
endfunction

" Temporary state container for global variables and options
function Tgsc(var,val,op) abort
    exec 'let l:p='.a:var
    exec 'let '.a:var.'="'.a:val.'"'
    call a:op()
    exec 'let '.a:var.'="'.l:p.'"'
endfunction
