document.addEventListener("click", function (e) {
    const target = e.target;

    // mod1
    if (target.matches("#btn-opn-mod1")) {
        document.querySelector("#modal1").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod1")) {
        document.querySelector("#modal1").style.display = "none";
        document.body.classList.remove('modal-open');
        closeModal();
    }

    // mod5
    if (target.matches("#btn-opn-mod5")) {
        document.querySelector("#modal5").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod5")) {
        document.querySelector("#modal5").style.display = "none";
        document.body.classList.remove('modal-open');
        closeModal();
    }

    // mod6
    if (target.matches("#btn-opn-mod6")) {
        document.querySelector("#modal6").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod6")) {
        document.querySelector("#modal6").style.display = "none";
        document.body.classList.remove('modal-open');
        closeModal();
    }

    // mod7
    if (target.matches("#btn-opn-mod7")) {
        document.querySelector("#modal7").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod7")) {
        document.querySelector("#modal7").style.display = "none";
        document.body.classList.remove('modal-open');
        closeModal();
    }

    // mod8
    if (target.matches("#btn-opn-mod8")) {
        document.querySelector("#modal8").style.display = "flex";
        document.body.classList.add('modal-open');
    }
    if (target.matches("#btn-clo-mod8")) {
        document.querySelector("#modal8").style.display = "none";
        document.body.classList.remove('modal-open');
        closeModal();
    }

    // delete pilgyo
    if (target.classList.contains("delete_pilgyo")) {
        const row = e.target.closest("tr");
        const uid = row.dataset.uid;

        const year = document.getElementById("year").value;
        const department = document.getElementById("department").value;
        const major = document.getElementById("major").value;

        const formData = new URLSearchParams();
        formData.append("type", 1);
        formData.append("year", year);
        formData.append("department", department);
        formData.append("major", major);
        formData.append("courses", uid);

        fetch("/exclude-course", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("mod_edit_pilgyo").innerHTML = html;
            }
        })
        .catch(err => console.error("삭제 실패:", err));
    }

    // delete comm
    if (target.classList.contains("delete_comm")) {
        const row = e.target.closest("tr");
        const uid = row.dataset.uid;

        const year = document.getElementById("year").value;
        const department = document.getElementById("department").value;
        const major = document.getElementById("major").value;

        const formData = new URLSearchParams();
        formData.append("year", year);
        formData.append("department", department);
        formData.append("major", major);
        formData.append("type", 2);
        formData.append("certs", uid);

        fetch("/delete-cert", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("mod_edit_comm").innerHTML = html;
            }
        })
        .catch(err => console.error("삭제 실패:", err));
    }
});

document.addEventListener("submit", function (e) {
    const form = e.target;
    if (!(form instanceof HTMLFormElement)) return;

    const id = form.id;
    e.preventDefault();
    // main_dashboard_search
    if (id === "search_dashboard") {
        const requiredFields = form.querySelectorAll("input[required]");
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
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("show_result").innerHTML = html;
            }
        })
        .catch(err => {
            console.error("!!! :", err);
        });
    }
    // edit credit
    else if (id === "edit_credit") {
        const year = document.getElementById("year").value;
        const department = document.getElementById("department").value;
        const major = document.getElementById("major").value;

        const formData = new URLSearchParams();
        formData.append("year", year);
        formData.append("department", department);
        formData.append("major", major);

        formData.append("pilgyo_credit", document.getElementById("pilgyo_cr_edit").value);
        formData.append("daegyo_credit", document.getElementById("daegyo_cr_edit").value);
        formData.append("jeontam_credit", document.getElementById("jeontam_cr_edit").value);
        formData.append("advanced_credit", document.getElementById("advanced_cr_edit").value);
        formData.append("jeonpil_credit", document.getElementById("jeonpil_cr_edit").value);
        formData.append("jeonseon_credit", document.getElementById("jeonseon_cr_edit").value);

        fetch("/edit-credit", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("mod_edit_credit").innerHTML = html;
            }
        })
        .catch(err => {
            console.error("수정 실패:", err);
        });
    }
    // edit number
    else if (id === "edit_num") {
        const checked = form.querySelectorAll("input[type='checkbox']:checked");

        const formData = new URLSearchParams();
        formData.append("year", document.getElementById("year").value);
        formData.append("department", document.getElementById("department").value);
        formData.append("major", document.getElementById("major").value);

        checked.forEach(cb => {
            formData.append("numbers", cb.value);
        });

        fetch("/edit-number", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("mod_edit_number").innerHTML = html;
            }
        })
        .catch(err => {
            console.error("영역 수정 실패:", err);
        });
    }
    // add comm
    else if (id === "insert_comm") {
        const requiredFields = form.querySelectorAll("input[required]");
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

        const year = document.getElementById("year").value;
        const department = document.getElementById("department").value;
        const major = document.getElementById("major").value;

        const formData = new URLSearchParams();
        formData.append("year", year);
        formData.append("department", department);
        formData.append("major", major);

        formData.append("name", document.getElementById("comm_name").value);
        formData.append("descript", document.getElementById("comm_descript").value);
        formData.append("score", document.getElementById("comm_score").value);
        formData.append("cert_type", 2);

        fetch("/add-cert", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("mod_edit_comm").innerHTML = html;
            }
        })
        .catch(err => {
            console.error("수정 실패:", err);
        });
    }
});

// exit modal and refresh
function closeModal(){
    const formData = new URLSearchParams();
        formData.append("department", document.getElementById("department").value);
        formData.append("year", document.getElementById("year").value);
        formData.append("major", document.getElementById("major").value);

        fetch("/academy_dashboard", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text();
            }
        })
        .then(html => {
            if (html) {
                document.getElementById("show_result").innerHTML = html;
            }
        })
        .catch(err => {
            console.error("!!! :", err);
        });
}

// ??
function showResult(){
    document.getElementById("inform").style.display = "block"
}