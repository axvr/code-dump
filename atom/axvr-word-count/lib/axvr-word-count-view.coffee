module.exports =
class AxvrWordCountView
  constructor: (serializedState) ->
    # Create root element
    @element = document.createElement('div')
    @element.classList.add('axvr-word-count')

    # Create message element
    message = document.createElement('div')
    message.textContent = "The AxvrWordCount package is Alive! It's ALIVE!"
    message.classList.add('message')
    @element.appendChild(message)

  # Returns an object that can be retrieved when package is activated
  serialize: ->

  # Tear down any state and detach
  destroy: ->
    @element.remove()

  getElement: ->
    @element

  setCount: (count, chars) ->
    displayText = "There are #{count} words, and #{chars} characters."
    @element.children[0].textContent = displayText
