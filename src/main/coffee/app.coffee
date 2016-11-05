$ ->
  $('[data-toggle="tooltip"]').tooltip()
  $('.chosen-select').chosen()
  $('[data-api="get"]').each ->
    target = $(@)
    data = target.data()
    selector = target
    if data.from
      $.ajax
        url: data.from
        success: (response) ->
          $(selector).text(response)
          response
