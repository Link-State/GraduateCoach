// modla1 control
const modal1 = document.querySelector('#modal1');
const btnOpenModal1 = document.querySelector('#btn-opn-mod1');
const btnCloseModal1 = document.querySelector('#btn-clo-mod1');
btnOpenModal1.addEventListener("click", ()=>{
    modal1.style.display="flex";
    document.body.classList.add('modal-open');
});
btnCloseModal1.addEventListener("click", ()=>{
    modal1.style.display="none"
    document.body.classList.remove('modal-open');
});

// modal2 control
const modal2 = document.querySelector('#modal2');
const btnOpenModal2 = document.querySelector('#btn-opn-mod2');
const btnCloseModal2 = document.querySelector('#btn-clo-mod2');
btnOpenModal2.addEventListener("click", ()=>{
    modal2.style.display="flex";
    document.body.classList.add('modal-open');
});
btnCloseModal2.addEventListener("click", ()=>{
    modal2.style.display="none"
    document.body.classList.remove('modal-open');
});


document.getElementById("search_dashboard").addEventListener("submit", function(e) {
    e.preventDefault();

    const formData = new URLSearchParams();
    formData.append("department", document.getElementById("department").value);
    formData.append("year", document.getElementById("year").value);
    formData.append("major", document.getElementById("major").value);

    fetch("/academy_dashboard/search_dashboard", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: formData.toString()
    })
    .then(response => response.text())
    .then(html => {
        document.getElementById("show_result").innerHTML = html;
    })
    .catch(err => {
        console.error("!!! :", err);
    });
});

// ??
function showResult(){
    document.getElementById("inform").style.display = "block"
}