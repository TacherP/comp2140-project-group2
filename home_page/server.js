//JS File for Landing Page

//Handles toggle menu in customerIndex
document.addEventListener("DOMContentLoaded", () => {
    const menuToggle = document.querySelector('.menu-toggle');
    const closeToggle = document.querySelector('.close-toggle');
    const nav = document.querySelector('nav');

// Open the menu
menuToggle.addEventListener('click', () => {
    nav.classList.add('active');
});

// Close the menu
closeToggle.addEventListener('click', () => {
    nav.classList.remove('active');
});
});

let currentSection = 0;
const sections = document.querySelectorAll('.section');

document.addEventListener('wheel', function (e){
    if (e.deltaY > 0 && currentSection < sections.length -1){
        currentSection++;
    } else if (e.deltaY < 0 && currentSection > 0){
        currentSection--;
    }
    window.scrollTo({top: sections[currentSection].offsetTop, behavior:'smooth'});
});
