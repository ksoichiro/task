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
  $sidebar = $('#sidebar')
  $mainContent = $('#main-content')
  toggleSidebar = ->
    $sidebar.toggleClass 'sidebar-open'
    $mainContent.toggleClass 'sidebar-open col-sm-offset-3 col-md-offset-2'

  $('[data-toggle="sidebar"]').on 'click', (e) ->
    # Prevent URL changes
    e.preventDefault()
    toggleSidebar()
    Cookies.set 'sidebar', $sidebar.hasClass('sidebar-open'), expires: 7

  $('.submit').on 'click', (e) ->
    $(this).parents('form').attr 'action', $(this).data('action')
    $(this).parents('form').submit()
