vim9script

# Zepl2


def zepl2#create_repl(config: dict<any>): number
    const cmd = config->get('command', '')

    if (cmd == '')
        throw 'No command given'
    endif

    const name = config->get('name', cmd .. ' [REPL]')

    const bufnr = CreatePromptBuffer(name, config->get('file-type', ''))

    ConnectPromptBuffer(bufnr, cmd)

    return bufnr
enddef


def CreatePromptBuffer(name: string, filetype: string): number
    const bufnr = bufadd(name)

    setbufvar(bufnr, '&buftype', 'prompt')
    setbufvar(bufnr, '&buflisted', true)

    if (filetype != '')
        setbufvar(bufnr, '&filetype', filetype)
    endif

    bufload(bufnr)

    return bufnr
enddef


def ConnectPromptBuffer(bufnr: number, cmd: string)
    g:repl_bufnr = bufnr

    execute('sbuffer ' .. bufnr)

    g:shell_job = job_start(cmd, {
        'out_cb': GotOutput,
        'err_cb': GotOutput,
        'exit_cb': JobExit
    })

    prompt_setcallback(bufnr, TextEntered)
    prompt_setprompt(bufnr, 'user=> ')

    # return g:shell_job
enddef


def GotOutput(channel: channel, msg: string)
    " TODO: use line number of previous prompt?
    appendbufline(g:repl_bufnr, len(getbufline(g:repl_bufnr, 1, '$')) - 1, msg)
enddef


def JobExit(job: job, status: string)
    quit!
enddef


def TextEntered(text: string)
    if (text != '')
        ch_sendraw(g:shell_job, text .. "\n")
    endif
enddef


# (clojure.main/repl :prompt (fn [] nil))


g:clj_bufnr = zepl2#create_repl({
    'name':      'Clojure [REPL]',      # Name of REPL buffer.
    'command':   'clojure',             # Command to start REPL.
    'file-type': 'clojure',             # File type to use in REPL buffer.
    # 'hist-file': '.clojure-repl.hist',  # Name of REPL input command history file.
    # 'preproc':   func,                  # Function to process input before sending to REPL.
    # 'postproc':  func,                  # Function to process output before printing to REPL buffer (or disable printing).
    # 'quitproc':  func,                  # Function which is run before killing the REPL (for clean up, or prompt quit).
    # Set-up proc?/continuation
    # Multiline input hack, ...
})


# zepl2#repl#init()  start repl.
# zepl2#repl#keys()  configure key bindings?

# Configuration variable to set start function.
#
# :Repl / :Clojure -> zepl2#lang#clojure#start()
# > Pick REPL: [1] Clojure (JVM), [2] ClojureScript, [3] Clojure CLR, etc.
#
# gz -> how to choose between multiple running REPLs?
