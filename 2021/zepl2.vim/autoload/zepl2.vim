vim9script

def zepl2#new_repl(config: dict<any>): number
    const cmd = config->get('command', '')

    if (cmd == '')
        throw 'No command given.'
    endif

    const name = config->get('name', cmd .. ' [REPL]')

    const bufnr = zepl2#create_prompt(name, config->get('file-type', ''))

    const job = zepl2#start_job(bufnr, config)

    return bufnr
enddef


def zepl2#create_prompt(name: string, filetype: string): number
    const bufnr = bufadd(name)

    setbufvar(bufnr, '&buftype', 'prompt')
    # setbufvar(bufnr, '&')
    setbufvar(bufnr, '&buflisted', true)

    if (filetype != '')
        setbufvar(bufnr, '&filetype', filetype)
    endif

    bufload(bufnr)

    return bufnr
enddef


def zepl2#start_job(bufnr: number, config: dict<any>): job
    execute('sbuffer ' .. bufnr)

    # TODO: use callbacks from config.
    const job = job_start(config->get('command'), {
        'out_cb': (channel, msg) => JobOutputCallback(bufnr, channel, msg),
        'err_cb': (channel, msg) => JobOutputCallback(bufnr, channel, msg),  # TODO: separate error callback.
        'exit_cb': (job, status) => JobExitCallback(bufnr, job, status),
        'pty': 1,
        'noblock': 1,
        'in_mode': 'raw',
        'out_mode': 'raw',
        'err_mode': 'raw',
        'drop': 'auto'
    })

    prompt_setcallback(bufnr, (text) => TextEntered(job, text))
    # prompt_setprompt(bufnr, 'user=> ')  # TODO: extract actual prompt.

    return job
enddef


def CleanOutput(str: string): string
    return substitute(str, '\r\n\+$', '', 'g')
enddef


def JobOutputCallback(bufnr: number, channel: channel, msg: string)
    appendbufline(bufnr, len(getbufline(bufnr, 1, '$')) - 1, CleanOutput(msg))
enddef


def JobExitCallback(bufnr: number, job: job, status: string)
    echomsg 'REPL closed.'
enddef


def TextEntered(job: job, text: string)
    if (text != '')
        echom text
        ch_sendraw(job, text .. "\n")
    endif
enddef


# TODO: function to send arbitrary text.
#       ^ option to send silently.


# TODO: emulate multi-line by making prompt blank and adding padding?


# zepl2#new_repl({
#     'name': 'Clojure [REPL]',
#     'command': 'clojure',
#     'file-type': 'clojure'
#     # 'preproc': ,
#     # 'postproc': ,
#     # 'quitproc': 
# })
