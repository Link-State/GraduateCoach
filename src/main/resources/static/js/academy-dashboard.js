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

            // ??
            function showResult(){
                document.getElementById("inform").style.display = "block"
            }