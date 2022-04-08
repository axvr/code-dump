vim9script

function! s:hexedit(type, old, new)
    " TODO: work with :set binary
    " FIXME: undo history.
    " TODO: Ascribe don't set readonly?
    " FIXME: reload buffer (or something else) to fix issue mentioned in :h 'edit-binary'
    if a:type ==# 'local' || a:type ==# 'auto'
        if !a:old && a:new
            let ro = &l:readonly
            let mod = &l:modified
            let b:hexedit_prevfiletype = &l:filetype

            setlocal noreadonly
            silent %!xxd
            setlocal filetype=xxd

            if !mod | setlocal nomodified | endif
            if ro | setlocal readonly | endif

            if a:type !=# 'auto'
                augroup hexedit
                    autocmd BufWritePre  <silent> <buffer> call s:hexedit('auto', &l:binary, 0)
                    autocmd BufWritePost <silent> <buffer> call s:hexedit('auto', &l:binary, 1)
                augroup END
            endif
        elseif a:old && !a:new
            let ro = &l:readonly
            let mod = &l:modified

            setlocal noreadonly
            %!xxd -r

            if exists('b:hexedit_prevfiletype')
                exec 'setlocal filetype=' . b:hexedit_prevfiletype
                unlet b:hexedit_prevfiletype
            else
                setlocal filetype<
            endif

            if !mod | setlocal nomodified | endif
            if ro | setlocal readonly | endif

            if a:type !=# 'auto'
                augroup hexedit
                    autocmd! BufWritePre  <buffer>
                    autocmd! BufWritePost <buffer>
                augroup END
            endif
        endif
    endif
endfunction


if executable('xxd')
    augroup hexedit
        au!
        autocmd OptionSet binary call <SID>hexedit(v:option_type, v:option_old, v:option_new)
    augroup END
endif

function! s:hexedit(value) dict
    if a:value
    endif
    " call s:set_bool_opt(['binary', 'readonly'], a:value)
endfunction

if exists('g:ascribe_handlers')
    let g:ascribe_handlers['binary'] = function('<SID>hexedit')
endif
