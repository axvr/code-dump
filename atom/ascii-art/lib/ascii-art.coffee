{CompositeDisposable} = require 'atom'

module.exports = AsciiArt =
  subscriptions: null

  activate: (state) ->
    @subscriptions = new CompositeDisposable
    @subscriptions.add atom.commands.add 'atom-workspace',
      'ascii-art:convert': => @convert()

  deactivate: ->
    @subscriptions.dispose()

  convert: ->
    console.log 'Convert text!'
    if editor = atom.workspace.getActiveTextEditor()
      editor.insertText('Hello World!')
