AxvrWordCountView = require './axvr-word-count-view'
{CompositeDisposable} = require 'atom'

module.exports = AxvrWordCount =
  axvrWordCountView: null
  modalPanel: null
  subscriptions: null

  activate: (state) ->
    @axvrWordCountView = new AxvrWordCountView(state.axvrWordCountViewState)
    @modalPanel = atom.workspace.addModalPanel(item: @axvrWordCountView.getElement(), visible: false)

    # Events subscribed to in atom's system can be easily cleaned up with a CompositeDisposable
    @subscriptions = new CompositeDisposable

    # Register command that toggles this view
    @subscriptions.add atom.commands.add 'atom-workspace', 'axvr-word-count:toggle': => @toggle()

  deactivate: ->
    @modalPanel.destroy()
    @subscriptions.dispose()
    @axvrWordCountView.destroy()

  serialize: ->
    axvrWordCountViewState: @axvrWordCountView.serialize()

  toggle: ->
    console.log 'AxvrWordCount was toggled!'

    if @modalPanel.isVisible()
      @modalPanel.hide()
    else
      editor = atom.workspace.getActiveTextEditor()
      words = editor.getText().split(/\s+/).length
      chars = editor.getText().split(/./).length
      @axvrWordCountView.setCount(words, chars)
      @modalPanel.show()
