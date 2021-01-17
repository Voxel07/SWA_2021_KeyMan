import React from 'react'

// id | country | department | name | postalcode | state | street
function Company({company}) {
    return (
        <div>
            <div key = {company.id}>Name : {company.country}</div>
            <div key = {company.id}>Street : {company.department}</div>
            <div key = {company.id}>Name : {company.name}</div>
            <div key = {company.id}>Street : {company.postalcode}</div>
            <div key = {company.id}>Name : {company.state}</div>
            <div key = {company.id}>Street : {company.street}</div>
        </div>
    )
}

export default Company
