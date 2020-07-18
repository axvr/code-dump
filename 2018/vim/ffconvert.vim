" Toggle between file formats (Unix → Dos, Dos → Unix, Mac → Unix)
" Public domain.
function! s:convert_file_format() abort
    let l:ff = &fileformat
    update
    edit ++fileformat=dos
    if l:ff !=# 'unix'
        setlocal fileformat=unix
    endif
    echomsg 'Converted buffer from' l:ff 'to' &l:ff
endfunction
command! -nargs=0 -bar ConvertFileFormat :call <SID>convert_file_format()
