const addEntry=document.getElementById("addButton");
const nameInput=document.getElementById("name");
const ageInput=document.getElementById("age");
const emailInput=document.getElementById("email");
const phoneInput=document.getElementById("phone");
const stateInput=document.getElementById("state");
const cityInput=document.getElementById("city");
const entryDetails=document.getElementById("entryDetails");
const formDetails=document.getElementById("dataEntryForm");

const formEntries=[];
const table = document.getElementById("dataTable");
addEntry.disabled=true;
addEntry.style.cursor="not-allowed";

const touchedFields={
    name:false,
    age:false,
    email:false,
    phone:false,
    branch:false,
    languages:false,
    state:false,
    city:false
}

function createErrorSpan(input){
    const p=document.createElement("p");
    p.className="errormsg";
    p.style.color="red";
    p.style.fontSize="16px";
    input.parentNode.appendChild(p);
    return p;
}
function success(){
    const p=document.createElement("p");
    p.className="successmsg";
    p.style.color="green";
    entryDetails.parentNode.appendChild(p);
    p.textContent="successfully added";
    setInterval(function(){
        p.textContent="";
    },1500);
}

const nameError=createErrorSpan(entryDetails);
const ageError=createErrorSpan(entryDetails);
const emailError=createErrorSpan(entryDetails);
const phoneError=createErrorSpan(entryDetails);
const branchError=createErrorSpan(entryDetails);
const langError=createErrorSpan(entryDetails);
const stateError=createErrorSpan(entryDetails);
const cityError=createErrorSpan(entryDetails);

function nameInputValidation(){
    const name=nameInput.value.trim();
    if(!(/^[a-zA-Z\s]{3,50}$/.test(name))){
        nameError.textContent="Enter valid name";
        return false;
    }
    nameError.textContent="";
    return true;
}
function ageInputValidation(){
    const age=ageInput.value;
    if(age<0 || age>122){
        ageError.textContent="Enter valid age";
        return false;
    }
    ageError.textContent="";
    return true;
}
function emailInputValidation(){
    const email=emailInput.value;
    if(!(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email))){
        emailError.textContent="Enter valid email";
        return false;
    }
    emailError.textContent="";
    return true;
}
function phoneInputValidation(){
    const phone=phoneInput.value;
    if(!(/^\d{10}$/.test(phone))){
        phoneError.textContent="Enter valid phone number (10 digits required)";
        return false;
    }
    phoneError.textContent="";
        return true;
}
function branchInputValidation(){
    const branchItems=document.getElementsByName("branch");
    for(let branch of branchItems){
        if(branch.checked){
            branchError.textContent="";
            return true;
        }
    }
    branchError.textContent="select the branch";
    return false;
}
function langInputValidation(){
    const langs=[
        document.getElementById("English"),
        document.getElementById("Hindi"),
        document.getElementById("Telugu")
    ];
    for(let lang of langs){
        if(lang && lang.checked){
            langError.textContent="";
            return true;
        }
    }
    langError.textContent="select atleast one language";
    return false;
}
function stateInputValidation(){
    const state=stateInput.value;
    if(!state){
        stateError.textContent="select state";
        return false;
    }
    stateError.textContent="";
    return true;
}
function cityInputValidation(){
    const city=cityInput.value;
    if(!city){
        cityError.textContent="select city";
        return false;
    }
    cityError.textContent="";
    return true;
}
function formValidation(){
    const isValid=
        (!touchedFields.name || nameInputValidation()) &&
        (!touchedFields.age || ageInputValidation()) &&
        (!touchedFields.email || emailInputValidation()) &&
        (!touchedFields.phone || phoneInputValidation()) &&
        (!touchedFields.branch || branchInputValidation()) &&
        (!touchedFields.languages || langInputValidation()) &&
        (!touchedFields.state || stateInputValidation()) &&
        (!touchedFields.city || cityInputValidation());
    addEntry.disabled=!isValid;
    addEntry.style.cursor=addEntry.disabled?"not-allowed":"pointer";
}

nameInput.addEventListener("change", ()=>{
    touchedFields.name=true;
    nameInputValidation();
    formValidation();
});
ageInput.addEventListener("change", ()=>{
    touchedFields.age=true;
    ageInputValidation();
    formValidation();
});
emailInput.addEventListener("change", ()=>{
    touchedFields.email=true;
    emailInputValidation();
    formValidation();
});
phoneInput.addEventListener("change", ()=>{
    touchedFields.phone=true;
    phoneInputValidation();
    formValidation();
});
const branchRadios=document.getElementsByName("branch");
for(let branchitem of branchRadios){
    branchitem.addEventListener("change",()=>{
        touchedFields.branch=true;
        branchInputValidation();
        formValidation();
    });
}
const languageCheckboxes=[
    document.getElementById("English"),
    document.getElementById("Hindi"),
    document.getElementById("Telugu")
];
for(let langCheck of languageCheckboxes){
    langCheck.addEventListener("change",()=> {
        touchedFields.languages=true;
        langInputValidation();
        formValidation();
    });
}
stateInput.addEventListener("change", ()=>{
    touchedFields.state=true;
    stateInputValidation();
    formValidation();
});
cityInput.addEventListener("change", ()=>{
    touchedFields.city=true;
    cityInputValidation();
    formValidation();
});

formDetails.addEventListener("submit",(e)=>{
    e.preventDefault();

    Object.keys(touchedFields).forEach(field=>touchedFields[field]=true);

    const isValid=
    nameInputValidation() &&
    ageInputValidation() &&
    emailInputValidation() &&
    phoneInputValidation() &&
    branchInputValidation() &&
    langInputValidation() &&
    stateInputValidation() &&
    cityInputValidation();

    addEntry.disabled=!isValid;
    addEntry.style.cursor=addEntry.disabled? "not-allowed" : "pointer";

    if(isValid){
        let selectedBranchValue="";
        branchRadios.forEach(branch=>{
            if(branch.checked){
                selectedBranchValue=branch.id.toLocaleUpperCase();
            }
        });

        let seleectedLanguages=[];
        languageCheckboxes.forEach(langCheck=>{
            if(langCheck.checked){
                seleectedLanguages.push(langCheck.id.slice(0,1).toLocaleUpperCase()+langCheck.id.slice(1).toLocaleLowerCase());
            }
        });
        let newEntry={
            name: nameInput.value.trim(),
            age:ageInput.value,
            email:emailInput.value.trim(),
            phone:phoneInput.value,
            branch:selectedBranchValue,
            language:seleectedLanguages,
            state:stateInput.value.slice(2),
            city:cityInput.value
        }
        formEntries.push(newEntry);
        addNewEntry(newEntry);

        success();

        nameInput.value="";
        ageInput.value="";
        emailInput.value="";
        phoneInput.value="";
        branchRadios.forEach(branch=>branch.checked=false);
        languageCheckboxes.forEach(langCheck=>{
            if(langCheck) langCheck.checked=false;
        });
        stateInput.value="";
        cityInput.value="";

        Object.keys(touchedFields).forEach(field=>touchedFields[field]=true);
        
        nameError.textContent="";
        ageError.textContent="";
        emailError.textContent="";
        phoneError.textContent="";
        branchError.textContent="";
        langError.textContent="";
        stateError.textContent="";
        cityError.textContent="";

        addEntry.disabled=true;
        addEntry.style.cursor="not-allowed";
    }
    else{
        formValidation();
    }
});

function addNewEntry(entry){
    const tb=document.querySelector("tbody");
    const row=table.insertRow();
    row.innerHTML=`
    <td>${entry.name}</td>
    <td>${entry.age}</td>
    <td>${entry.email}</td>
    <td>${entry.phone}</td>
    <td>${entry.branch}</td>
    <td>${entry.language.join(",")}</td>
    <td>${entry.state}</td>
    <td>${entry.city}</td>
    <td class="action">
        <button class="editBtn"><i class="fa fa-pencil"></i>    Edit</button>
        <button class="deleteBtn"><i class="fa fa-close"></i>   Delete</button>
    </td>`;
    tb.appendChild(row);
    const editBtn = row.querySelector(".editBtn");
    const deleteBtn = row.querySelector(".deleteBtn");

    deleteBtn.addEventListener("click", () => {
        deleteIndex = formEntries.indexOf(entry);
        deleteRow = row;
        showDeletePopup(); 
    });


    editBtn.addEventListener("click", () => {
        nameInput.value = entry.name;
        ageInput.value = entry.age;
        emailInput.value = entry.email;
        phoneInput.value = entry.phone ; 
        stateInput.value = entry.state;
        cityInput.value = entry.city;

        branchRadios.forEach(branch => {
            branch.checked = (branch.id.toLowerCase() === entry.branch.toLowerCase());
        });

        languageCheckboxes.forEach(langCheck => {
            langCheck.checked = entry.language.some(lang => {
                return lang.toLowerCase() === (langCheck.id.toLowerCase());
            });
        });

        const index = formEntries.indexOf(entry);
        if (index > -1) {
            formEntries.splice(index, 1);
        }
        table.deleteRow(row.rowIndex);

        addEntry.disabled = false;
        addEntry.style.cursor = "pointer";
    });
}


document.getElementById("confirmDelete").addEventListener("click", () => {
    if (deleteIndex !== null && deleteRow !== null) {
        formEntries.splice(deleteIndex, 1);
        table.deleteRow(deleteRow.rowIndex);
    }
    hideDeletePopup();
    deleteIndex = null;
    deleteRow = null;
});

document.getElementById("cancelDelete").addEventListener("click", () => {
    deleteIndex = null;
    deleteRow = null;
    hideDeletePopup();
});

function showDeletePopup() {
    document.getElementById("deletePopup").style.display = "block";
}

function hideDeletePopup() {
    document.getElementById("deletePopup").style.display = "none";
}

document.querySelectorAll('.filter').forEach(input => {
    input.addEventListener("input", function () {
        const col = parseInt(this.getAttribute('data-col'));
        const filterText = this.value.toLowerCase();
        const rows = document.querySelectorAll('#dataTable tbody tr');
        let visibleRowsCount = 0;

        rows.forEach(row => {
            const cell = row.cells[col];
            if (cell) {
                const cellText = cell.textContent.toLowerCase();
                const matches = cellText.startsWith(filterText);
                row.style.display = matches ? "" : "none";
            }
        });

        rows.forEach(row => {
            if (row.style.display !== "none") {
                visibleRowsCount++;
            }
        });

        const noResultsMessage = document.getElementById("noResults");
        if (visibleRowsCount === 0 && formEntries.length > 0) {
            noResultsMessage.style.display = "block";
        } else {
            noResultsMessage.style.display = "none";
        }
    });
});


fetch(`http://192.168.0.73:32114/partner/get-states?countryCode=IN`)
    .then(response => response.json())
    .then(fullResponse => {
        stateInput.innerHTML = '<option value="">Select State</option>';
        const statesData = JSON.parse(fullResponse.response);
    Object.entries(statesData).forEach(([name, code]) => {
        const option = document.createElement('option');
        option.value = code+name;
        option.textContent = name;
        stateInput.appendChild(option);
    });
    })
    .catch(error => {
    console.error('API Error for states:', error);
    });

stateInput.addEventListener('change', () => {
    const selectedStateCode = stateInput.value.slice(0,2);
    cityInput.innerHTML = '<option value="">Select City</option>';

    if (!selectedStateCode) return;

    fetch(`http://192.168.0.73:32114/partner/get-cities-for-state?stateCode=${selectedStateCode}`)
    .then(response => response.json())
    .then(data => {
        const citiesData = JSON.parse(data.response);
        Object.entries(citiesData).forEach(([name, code]) => {
        const option = document.createElement('option');
        option.value = name;
        option.textContent =name;
        cityInput.appendChild(option);
        });
    })
    .catch(error => {
        console.error('API Error for cities:', error);
    });
});