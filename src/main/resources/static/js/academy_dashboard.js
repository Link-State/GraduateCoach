document.addEventListener("click", function (e) {
    const target = e.target;

    // mod1
    if (target.matches("#btn-opn-mod1")) {
        document.querySelector("#modal1").style.display = "flex";
        document.body.classList.add('modal-open');
        // const department = document.getElementById("department").value;
            // const year = document.getElementById("year").value;
            // const major = document.getElementById("major").value;

            // const formData = new URLSearchParams();
            // formData.append("department", department);
            // formData.append("year", year);
            // formData.append("major", major);

            // fetch("/academy_dashboard/edit/pilgyo", {
            //     method: "POST",
            //     headers: { "Content-Type": "application/x-www-form-urlencoded" },
            //     body: formData.toString()
            //   })
            //     .then(res => res.text())
            //     .then(html => {
            //       tableBody.innerHTML = html;
            //       bindCheckboxEvents();
            //     })
            //     .catch(err => {
            //       console.error("불러오기 실패:", err);
            //     });
            // }
    }
    if (target.matches("#btn-clo-mod1")) {
        document.querySelector("#modal1").style.display = "none";
        document.body.classList.remove('modal-open');
    }

    // mod5
    if (target.matches("#btn-opn-mod5")) {
        document.querySelector("#modal5").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod5")) {
        document.querySelector("#modal5").style.display = "none";
        document.body.classList.remove('modal-open');
    }

    // mod6
    if (target.matches("#btn-opn-mod6")) {
        document.querySelector("#modal6").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod6")) {
        document.querySelector("#modal6").style.display = "none";
        document.body.classList.remove('modal-open');
    }

    // mod7
    if (target.matches("#btn-opn-mod7")) {
        document.querySelector("#modal7").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod7")) {
        document.querySelector("#modal7").style.display = "none";
        document.body.classList.remove('modal-open');
    }
});

// main_dashboard_search
document.getElementById("search_dashboard").addEventListener("submit", function(e) {
    e.preventDefault();

    const requiredFields = this.querySelectorAll("input[required]");
    let hasEmpty = false;

    for (const field of requiredFields) {
        if (!field.value.trim()) {
            field.classList.add("input-warning");
            hasEmpty = true;
        } else {
            field.classList.remove("input-warning");
        }
    }

    if (hasEmpty) return;

    const formData = new URLSearchParams();
    formData.append("department", document.getElementById("department").value);
    formData.append("year", document.getElementById("year").value);
    formData.append("major", document.getElementById("major").value);

    fetch("/academy_dashboard", {
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