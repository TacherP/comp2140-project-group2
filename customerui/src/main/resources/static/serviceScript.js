// Hide header on scroll
document.addEventListener("DOMContentLoaded", () => {
    const filterButtons = document.querySelectorAll(".filter-btn");
    const serviceItems = document.querySelectorAll(".service-item");
    const header = document.querySelector("header");
    const toggleBtn = document.querySelector(".toggle-btn");
    const closeBtn = document.querySelector(".close-btn");
    const nav = document.querySelector('nav');

    // Filter functionality
    filterButtons.forEach(button => {
        button.addEventListener("click", () => {
            filterButtons.forEach(btn => btn.classList.remove("selected"));
            button.classList.add("selected");

            const filter = button.getAttribute("data-filter");
            serviceItems.forEach(item => {
                if (filter === "all" || item.getAttribute("data-category") === filter) {
                    item.style.display = "block";
                } else {
                    item.style.display = "none";
                }
            });
        });
    });

    // Dynamic header show/hide on scroll
    let lastScrollY = window.scrollY;

    window.addEventListener("scroll", () => {
        if (window.scrollY > lastScrollY) {
            header.classList.add("hidden"); // Hide header when scrolling down
        } else {
            header.classList.remove("hidden"); // Show header when scrolling up
        }
        lastScrollY = window.scrollY;
    });

    // Get all book now buttons
    const bookButtons = document.querySelectorAll('.book-btn');
    const modal = document.getElementById("appointmentModal");
    const scheduleBtn = document.getElementById("schedule-btn");
    const closeButton = document.querySelector(".close");
    // Function to open the modal with service details
    bookButtons.forEach(button => {
        button.addEventListener("click", function() {
            // Get the service details from the clicked item
            const serviceItem = button.closest('.service-item');
            // Debugging logs to see if the serviceItem is correctly found
            if (!serviceItem) {
                console.error('Service item not found.');
                return;
            }

             // Extract the service name, price, and duration
             const serviceName = serviceItem.querySelector('h2') ? serviceItem.querySelector('h2').innerText : 'Service Name Not Found';
             const serviceDuration = serviceItem.querySelector('p:nth-child(3)') ? serviceItem.querySelector('p:nth-child(3)').innerText.trim() : 'Duration Not Found';
             const servicePrice = serviceItem.querySelector('p:last-of-type') ? serviceItem.querySelector('p:last-of-type').innerText.trim().replace('$', '').replace(',', '') : 'Price Not Found';

            
             // Debugging logs
            console.log('Service Name:', serviceName);
            console.log('Service Duration:', serviceDuration);
            console.log('Service Price:', servicePrice);

            // Update modal content with service details
            document.getElementById("service-name").innerText = serviceName;
            document.getElementById("service-price").innerText = servicePrice;
            document.getElementById("service-duration").innerText = serviceDuration;
            
            // Show the modal
            modal.style.display = "block";
        });
    });

    // When the "Schedule Appointment" button is clicked
    scheduleBtn.addEventListener("click", function () {
        // Get the service details from the modal
        const serviceName = document.getElementById("service-name").innerText;
        const servicePrice = document.getElementById("service-price").innerText;
        const serviceDuration = document.getElementById("service-duration").innerText;

        // Get customer info from the input form (you should have form elements for customer data)
        const firstName = document.getElementById("first-name").value;
        const lastName = document.getElementById("last-name").value;
        const email = document.getElementById("email").value;
        const phone = document.getElementById("phone").value;
        const address = document.getElementById("address").value;

        // Construct customer object
        const customer = {
            firstName: document.getElementById("first-name").value,
            lastName: document.getElementById("last-name").value,
            email: document.getElementById("email").value,
            phone: document.getElementById("phone").value,
            address: document.getElementById("address").value,
        };

        // Send the data to the backend to create the appointment
        fetch("http://localhost:8081/appointments/book", {
    method: "POST",
    headers: {
        "Content-Type": "application/json", // Send JSON payload
        "Accept": "application/json" // Expect JSON response
    },
            body: JSON.stringify({
                serviceName,
                servicePrice,
                serviceDuration,
                customer,
            }),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                alert("Appointment scheduled successfully!");
                modal.style.display = "none";
            } else {
                alert(data.error || "Error scheduling appointment. Please try again.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("An error occurred. Please try again.");
        });
    });
    
    // Close the modal when the close button is clicked
    closeButton.addEventListener("click", function() {
        modal.style.display = "none";
    });
    
    // Close the modal if the user clicks outside of it
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});