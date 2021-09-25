vim9script

# Zepl2

def TextEntered(text)
    ch_sendraw()
enddef

def GetOutput(channel, msg)
    append(line("$") - 1, "- " . msg)
enddef

def JobExit(job, status)
    quit!
enddef

def StartJob(cmd: string): number
    ch_logfile('clojure-repl.log', 'w')
    final job = job_start([cmd], #{
        out_cb: function('GotOutput'),
        err_cb: function('GotOutput'),
        exit_cb: function('JobExit')
    })
    const channel = job_getchannel(job)
    return channel
enddef

def CreatePromptBuffer(name: string, filetype: string): number
    const bufnr = bufadd(name)
    setbufvar(bufnr, '&buftype', 'prompt')
    setbufvar(bufnr, '&buflisted', true)
    setbufvar(bufnr, '&filetype', filetype)
    bufload(bufnr)

    # sbuffer bufnr

    return bufnr
enddef

def Clojure_SetPrompt(bufnr: number, namespace: string)
    if bufexists(bufnr)
        prompt_setprompt(bufnr, namespace .. '=> ')
    endif
enddef

var bufnr = CreatePromptBuffer('Clojure [REPL]', 'clojure')
Clojure_SetPrompt(bufnr, 'user')

# inoremap <buffer> <silent> <C-l> <C-o>z<CR>
# TODO: jump back to correct column
#
# <C-d> Y-n? confirm()
#
# Basic readline bindings.  <C-a> <C-e> <C-k> <C-l> <C-d> <C-c> <C-f> <C-b>
#
# Click row to copy it into new prompt?

# Create prompt buffer.
# Set key bindings.
# Set prompt.
#
# Multiline prompt.
# Input history.
# Proper copy/paste support.
#
# Classes not added to vim9 yet.
#
# Multiple REPL support.
#
# Bring back gz command.
#
# Can this be a backwards compatible with existing Zepl?  I.e. Can Zeplv1 be
# reimplemented on this new system?  Or just archive old Zepl and name this
# one Zepl2 (or use v2 branch)?


# Repl object.
#   Create prompt buffer.
#   Start job.
#   Bind buffer to job.
#   Set keybindings.
#   Set filetype for syntax highlighting and indentation.
#
#   On text entered, handle multiline.
