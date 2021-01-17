import React from 'react'

// id | endDate | licenskey | startDate | version | company_Id 
function Contract({contract}) {
    return (
        <div>
            <div key = {contract.id}>Name : {contract.endDate}</div>
            <div key = {contract.id}>Street : {contract.licenskey}</div>
            <div key = {contract.id}>Name : {contract.startDate}</div>
            <div key = {contract.id}>Street : {contract.postversionalcode}</div>
            <div key = {contract.id}>Name : {contract.company_Id}</div>
        </div>
    )
}

export default Contract
