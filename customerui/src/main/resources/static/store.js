//JS File for Storefront
document.addEventListener("DOMContentLoaded", () => {
    const slides = document.querySelector(".slides");
    const prevBtn = document.querySelector(".prev-btn");
    const nextBtn = document.querySelector(".next-btn");
    const slideElements = document.querySelectorAll(".slide");
    const header = document.querySelector(".header-bar-section"); // Hide header on scroll

    let currentIndex = 0;
    const totalSlides = slideElements.length;

    function showSlide(index) {
        slides.style.transform = `translateX(-${index * 100}%)`;
    }

    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex === 0) ? totalSlides - 1 : currentIndex - 1;
        showSlide(currentIndex);
    });

    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex === totalSlides - 1) ? 0 : currentIndex + 1;
        showSlide(currentIndex);
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

    document.addEventListener("DOMContentLoaded", function () {
        const menuToggle = document.querySelector(".menu-toggle");
        const closeToggle = document.querySelector(".close-toggle");
        const nav = document.querySelector("nav");

        // Open the menu
        menuToggle.addEventListener('click', () => {
            nav.classList.add('active');
        });

        // Close the menu
        closeToggle.addEventListener('click', () => {
            nav.classList.remove('active');
        });
    });

    // Auto-slide every 5 seconds
    setInterval(() => {
        currentIndex = (currentIndex === totalSlides - 1) ? 0 : currentIndex + 1;
        showSlide(currentIndex);
    }, 5000);

    // Dynamic Background Color Script 
    slideElements.forEach((slide) => {
        const img = slide.querySelector("img");
        if (img.complete) {
            // If the image is already loaded
            applyDominantColor(img, slide);
        } else {
            // Wait for the image to load
            img.onload = () => applyDominantColor(img, slide);
        }
    });

    function applyDominantColor(img, slide) {
        const canvas = document.createElement("canvas");
        const context = canvas.getContext("2d");

        // Resize canvas to the image dimensions
        canvas.width = img.width;
        canvas.height = img.height;

        context.drawImage(img, 0, 0, canvas.width, canvas.height);

        const imageData = context.getImageData(0, 0, canvas.width, canvas.height).data;

        let r = 0, g = 0, b = 0, count = 0;

        // Loop through image data to calculate average color
        for (let i = 0; i < imageData.length; i += 4) {
            r += imageData[i];       // Red
            g += imageData[i + 1];   // Green
            b += imageData[i + 2];   // Blue
            count++;
        }

        // Calculate average RGB values
        r = Math.floor(r / count);
        g = Math.floor(g / count);
        b = Math.floor(b / count);

        // Apply the calculated color as the slide background
        slide.style.backgroundColor = `rgb(${r}, ${g}, ${b})`;
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const products = [
        {
            id: 1, name: "Macadamia Massage Soap Bar", description: "Crafted with rich, nourishing macadamia oil to provide a luxurious and soothing experience.", price: 1500, img: "images/macadamia-soap.jpg"
        },
        {
            id: 2, name: "Dry Brush", description: "Revitalize your skin with our luxe body brush, designed to exfoliate and invigorate.", price: 2000, img: "images/brush-dry-massage.jpg"
        },
        {
            id: 3, name: "All Things Coffee", description: "Indulge in the ultimate coffee lover's treat. Premium coffee ready to brew, rejuvenating coffee-infused face and body masks.", price: 6000, img: "images/coffee-package.jpg"
        },
        {
            id: 4, name: "Quoted Tea Cups", description: "Add a little personality to your tea time with our charming quoted tea cups.", price: 2000, img: "images/tea-cup.jpg"
        }
    ];

    const modal = document.getElementById("productModal");
    const productName = document.getElementById("productName");
    const productDescription = document.getElementById("productDescription");
    const productPrice = document.getElementById("productPrice");
    const productImage = document.getElementById("productImage");
    const addToCartButton = document.querySelector(".add-to-cart");
    const modalPrevBtn = document.querySelector(".prevv-btn");
    const modalNextBtn = document.querySelector(".nexxt-btn");
    const closeBtn = document.querySelector(".close");

    let currentIndex = 0;
    const cart = JSON.parse(localStorage.getItem('cart')) || []; // Retrieve cart from localStorage


    // Function to open modal and display product details
    function openModal(index) {
        const product = products[index];
        productName.textContent = product.name;
        productDescription.textContent = product.description;
        productPrice.textContent = product.price;
        productImage.src = product.img; // Update the product image
        modal.style.display = "block";
        currentIndex = index;
    }

    // Close modal
    closeBtn.onclick = () => {
        modal.style.display = "none";
    };

        // Navigate to next product
        modalNextBtn.onclick = () => {
            currentIndex = (currentIndex + 1) % products.length; // Wrap around to the first product
            openModal(currentIndex); // Update modal content
        };

         // Navigate to previous product
        modalPrevBtn.onclick = () => {
            currentIndex = (currentIndex - 1 + products.length) % products.length; // Wrap around to the last product
            openModal(currentIndex); // Update modal content
        };
    
        // Click outside modal to close
        window.onclick = (event) => {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        };
    
        // Add click event to each product
        document.querySelectorAll(".product").forEach((productElement, index) => {
            productElement.addEventListener("click", () => {
                openModal(index);
            });
        });

        //add to cart button fuction
        // Add item to cart
    addToCartButton.addEventListener("click", () => {
        const product = products[currentIndex];
        addToCart(product);
        modal.style.display = "none"; // Close modal after adding item
    });
        function addToCart(product) {
            const existingItem = cart.find(item => item.id === product.id);
            if (existingItem) {
                existingItem.quantity += 1; // Increase quantity if item already exists
            } else {
                cart.push({ ...product, quantity: 1 }); // Add new item
            }
            localStorage.setItem('cart', JSON.stringify(cart)); // Save updated cart to localStorage
            updateCartModal(); // Update cart modal UI
        }
});
    

    document.addEventListener("DOMContentLoaded", () => {
    const cartIcon = document.querySelector(".cart-icon");
    const cartModal = document.getElementById("cartModal");
    const cartList = document.getElementById("cartList");
    const cartTotal = document.getElementById("cartTotal");
    const clearCartButton = document.getElementById("clearCart");
    const checkoutButton = document.getElementById("checkout");
    const closeCartButton = document.querySelector(".close-cart");

    // Retrieve cart from localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];

    //cart UI
    function updateCartUI() {
        cartList.innerHTML = ''; // Clear current cart list
        let total = 0;
        
        // Loop through the cart items and render them
        cart.forEach(item => {
            const li = document.createElement("li");
            li.classList.add("cart-item");

        // Add product image and name with quantity and price
        listItem.innerHTML = `
            <img src="${item.img}" alt="${item.name}" class="cart-item-img">
            <span class="cart-item-name">${item.name} x${item.quantity}</span>
            <span class="cart-item-price">$${item.price}</span>
            <button class="remove-item-btn" data-id="${item.id}">Remove</button>
        `;
        cartList.appendChild(li);
        total += item.price * item.quantity;
    });
    cartTotal.textContent = `Total: $${total.toFixed(2)}`;

    // event listeners to remove buttons
    document.querySelectorAll(".remove-item-btn").forEach(button => {
        button.addEventListener("click", (e) => {
            const itemId = e.target.dataset.id;
            removeItemFromCart(itemId);
        });
    });
}

    // Open the cart modal
    cartIcon.addEventListener("click", () => {
        cartModal.style.display = "none";
    });

    // Close the cart modal
    closeCartButton.addEventListener("click", () => {
        cartModal.style.display = "none";
    });

    // Clear the cart
    clearCartButton.addEventListener("click", () => {
        localStorage.removeItem('cart');
        cart.length = 0; // Clear the local cart array
        updateCartUI();
    });
    
    // Checkout button (can be extended for checkout functionality)
    checkoutButton.addEventListener("click", () => {
        alert("Proceeding to checkout...");
        // Redirect to checkout pop up
    });

    // Initial call to update the cart UI when page loads
    updateCartUI();
});

document.addEventListener("DOMContentLoaded", () => {
    const checkoutButton = document.getElementById("checkout");
    const checkoutModal = document.getElementById("checkoutModal");
    const closeModal = document.getElementById("closeModal");
    const placeOrderButton = document.getElementById("placeOrderButton");
    const checkoutForm = document.getElementById("checkoutForm");

    // Show the pop-up when checkout is clicked
    checkoutButton.addEventListener("click", () => {
        checkoutModal.style.display = "flex"; // Show the modal
    });

    // Close the modal when the close button (x) is clicked
    closeModal.addEventListener("click", () => {
        checkoutModal.style.display = "none"; // Hide the modal
    });

    // Handle the form submission
    checkoutForm.addEventListener("submit", (event) => {
        event.preventDefault(); // Prevent the form from submitting the usual way

        // Capture form data
        const formData = new FormData(checkoutForm);
        const customerDetails = Object.fromEntries(formData.entries());

        console.log("Order Details:", customerDetails);

        // Close the modal after submitting the order
        checkoutModal.style.display = "none";

        // Send the data to the backend API for further processing
        fetch("/processOrder", {
            method: "POST",
            body: JSON.stringify(customerDetails),  // Convert form data to JSON
            headers: { "Content-Type": "application/json" }
        })
            .then(response => response.json())
            .then(data => {
                console.log("Order successfully placed!", data);
            })
            .catch(error => console.error("Error processing order:", error));
    });

    // Close the modal if the user clicks outside of the modal content
    window.addEventListener("click", (event) => {
        if (event.target === checkoutModal) {
            checkoutModal.style.display = "none";
        }
    });
});



 




