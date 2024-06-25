// Click event for chat-button
$('.chat-button').on('click', function() {
    // Hide the chat button
    $('.chat-button').css({
        "display": "none"
    });
    // Make the chat box visible
    $('.chat-box').css({
        "visibility": "visible"
    });
});

// Click event for closing the chat-box
$('.chat-box .chat-box-header p').on('click', function() {
    // Show the chat button
    $('.chat-button').css({
        "display": "block"
    });
    // Hide the chat box
    $('.chat-box').css({
        "visibility": "hidden"
    });
});

// Click event for showing the modal
$("#addExtra").on("click", function() {
    // Toggle the show-modal class on the modal
    $(".modal").toggleClass("show-modal");
});

// Click event for closing the modal
$(".modal-close-button").on("click", function() {
    // Toggle the show-modal class on the modal
    $(".modal").toggleClass("show-modal");
});
