import React from 'react'

// id | email | first_name | is_admin | last_name | password | username | company_Id
function User({user}) {
    return (
        <div>
            <div key = {user.id}>Name : {user.email}</div>
            <div key = {user.id}>Street : {user.first_name}</div>
            <div key = {user.id}>Name : {user.is_admin}</div>
            <div key = {user.id}>Street : {user.last_name}</div>
            <div key = {user.id}>Name : {user.password}</div>
            <div key = {user.id}>Street : {user.username}</div>
            <div key = {user.id}>Street : {user.company_Id}</div>
        </div>
    )
}

export default User
