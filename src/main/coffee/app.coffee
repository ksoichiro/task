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

  # Toggle showing/hiding sidebar
  $('[data-toggle="sidebar"]').on 'click', (e) ->
    # Prevent URL changes
    e.preventDefault()
    $('#sidebar').toggleClass 'sidebar-open'
    $('#main-content').toggleClass 'disable-offset'
