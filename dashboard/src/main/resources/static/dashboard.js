// Bar Chart
const barCtx = document.getElementById('barChart').getContext('2d');
new Chart(barCtx, {
  type: 'bar',
  data: {
    labels: ['01', '02', '03', '04', '05', '06', '07'],
    datasets: [
      {
        label: 'Active Users',
        data: [600, 800, 750, 900, 1000, 850, 950],
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
      },
    ],
  },
});

// Line Chart
const lineCtx = document.getElementById('lineChart').getContext('2d');
new Chart(lineCtx, {
  type: 'line',
  data: {
    labels: ['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct'],
    datasets: [
      {
        label: 'Traffic',
        data: [100, 200, 300, 400, 500, 600, 700],
        borderColor: 'rgba(75, 192, 192, 1)',
        fill: false,
      },
      {
        label: 'Sales',
        data: [150, 250, 350, 450, 550, 650, 750],
        borderColor: 'rgba(153, 102, 255, 1)',
        fill: false,
      },
    ],
  },
});

document.addEventListener('DOMContentLoaded', function() {
//Appointments in sidebar
const appointmentsLink = document.querySelector('.appointments-link');
const calendarContainer = document.getElementById('calendar'); // calendar container
// Initialize FullCalendar only once
let calendarInitialized = false;
let calendar;

appointmentsLink.addEventListener('click', function(event) {
    event.preventDefault();
    
    // Show calendar and hide other content
    document.querySelector('.dashboard-content').style.display = 'none'; // Hide other content
    calendarContainer.style.display = 'block'; // Show calendar

    // Initialize FullCalendar
    if (!calendarInitialized) {
        calendar = new FullCalendar.Calendar(calendarContainer, {
            initialView: 'dayGridMonth', // Default view is month
            events: [
                {
                    title: 'Appointment 1',
                    start: '2024-11-25T10:00:00',
                    end: '2024-11-25T11:00:00'
                },
                {
                    title: 'Appointment 2',
                    start: '2024-11-26T12:00:00',
                    end: '2024-11-26T13:00:00'
                }
            ]
        });

        calendar.render(); // Render the calendar
        calendarInitialized = true;
    }
});

const customersLink = document.querySelector('.customers-link');
    if (customersLink) {
        customersLink.addEventListener('click', function(event) {
            event.preventDefault();
            document.querySelector('.dashboard-content').style.display = 'none';
            document.querySelector('#customer-container').style.display = 'block';
        });
    } else {
        console.error('Customers link not found.');
    }

/*// Dashboard link click event to return to the main content
const dashboardLink = document.querySelector('.dashboard-link');
    if (dashboardLink) {
        dashboardLink.addEventListener('click', function(event) {
            event.preventDefault();
            document.querySelector('.dashboard-content').style.display = 'block';
            document.getElementById('calendar').style.display = 'none';
        });
    } else {
        console.error('Dashboard link not found.');
    }*/

        document.getElementById("backButton").addEventListener("click", function() {
            document.querySelector('.dashboard-content').style.display = 'block';
            document.getElementById('calendar').style.display = 'none';
        });        

//log out button
const logoutButton = document.getElementById("logoutButton");
    if (logoutButton) {
        logoutButton.addEventListener("click", async function() {
            try {
                const response = await fetch('/admin/logout', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                const data = await response.json();
                if (response.ok) {
                    alert(data.message); 
                    window.location.href = '/';
                } else {
                    alert("Error during logout");
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Failed to communicate with the server.");
            }
        });
    } else {
        console.error('Logout button not found.');
    }
});


document.getElementById("addServiceForm").addEventListener("submit", async (event) => {
event.preventDefault();

const serviceName = document.getElementById("serviceName").value;
const serviceDescription = document.getElementById("serviceDescription").value;
const servicePrice = document.getElementById("servicePrice").value;

try {
  const response = await fetch("http://localhost:8080/adminDashboard/addService", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `serviceName=${serviceName}&serviceDescription=${serviceDescription}&servicePrice=${servicePrice}`,
  });

  if (response.ok) {
    alert("Service added successfully!");
  } else {
    alert("Error adding service.");
  }
} catch (error) {
  console.error("Error:", error);
  alert("Failed to communicate with the server.");
}

// Inventory link
const inventoryLink = document.querySelector('.inventory-link');
if (inventoryLink) {
    inventoryLink.addEventListener('click', function(event) {
        event.preventDefault();
        document.querySelector('.dashboard-content').style.display = 'none';
        document.getElementById('inventory').style.display = 'block';
        fetchInventory();
    });
} else {
    console.error('Inventory link not found.');
}
});

// Inventory fetching
async function fetchInventory() {
try {
    const response = await fetch('/admin/inventory');
    const inventoryData = await response.json();
    const inventoryList = document.getElementById('inventoryList');
    inventoryList.innerHTML = '';
    inventoryData.forEach(item => {
        const div = document.createElement('div');
        div.textContent = `${item.productName} - ${item.quantityInStock} in stock`;
        inventoryList.appendChild(div);
    });
} catch (error) {
    console.error('Error fetching inventory:', error);
}
}

function toggleForm() {
const formContainer = document.getElementById("addServiceFormContainer");
formContainer.style.display = formContainer.style.display === "none" ? "block" : "none";
}


document.getElementById("removeServiceForm").addEventListener("submit", function (event) {
event.preventDefault();

const serviceId = document.getElementById("serviceIdToRemove").value;

// Call backend logic or API
console.log("Removing service with ID:", serviceId);

// Close the modal and reset the form
document.getElementById("removeServiceForm").reset();
const removeServiceModal = bootstrap.Modal.getInstance(document.getElementById("removeServiceModal"));
removeServiceModal.hide();
});

document.getElementById("modifyServiceButton").addEventListener("click", function() {
// You can handle the logic here, e.g., check if the service exists
const serviceId = document.getElementById("serviceIdToModify").value;

// Make an API call to check if the service exists (this is just an example)
fetch(`/checkServiceExists?serviceId=${serviceId}`)
  .then(response => response.json())
  .then(data => {
    if (data.exists) {
      // If service exists, show the modal
      const modifyServiceModal = new bootstrap.Modal(document.getElementById('modifyServiceModal'));
      modifyServiceModal.show();
    } else {
      // If service does not exist, show an error message
      alert("Service not found.");
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });
});


