/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function togglerElement(toggledElementId, togglerElementId, className1, className2, labelContent1, labelContent2) {
    const toggledElement = document.getElementById(toggledElementId);
    const togglerElement = document.getElementById(togglerElementId);
    const labelElement = document.querySelector(`#${togglerElementId} + label`);

    function toggleClassName(cName1, cName2, labelContent) {
        toggledElement.classList.add(cName2);
        toggledElement.classList.remove(cName1);
        toggledElement.toggleAttribute('readonly');
        
        labelElement.innerText = labelContent;
    }

    togglerElement.addEventListener('change', () => {
        if (togglerElement.checked) {
            toggleClassName(className1, className2, labelContent2);
        } else {
            toggleClassName(className2, className1, labelContent1);
        }
    });
}
