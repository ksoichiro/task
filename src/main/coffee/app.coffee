$ ->
  $('[data-toggle="tooltip"]').tooltip()
  $('.chosen-select').chosen()

@get = (url, selector) ->
  $.ajax
    url: url
    success: (response) ->
      $(selector).text(response)
      response
