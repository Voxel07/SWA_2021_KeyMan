(this.webpackJsonpwebapp=this.webpackJsonpwebapp||[]).push([[0],{33:function(e,t,n){},34:function(e,t,n){},35:function(e,t,n){},59:function(e,t,n){"use strict";n.r(t);var a=n(0),s=n(1),o=n(25),r=n.n(o),c=n(27);n(33),n.p,n(34);var i=n(11),l=n(4),u=n(5),h=n(6),d=n(9),j=n(8),b=(n(35),n(7)),p=n.n(b);s.Component,Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));var m=function(e){Object(d.a)(n,e);var t=Object(j.a)(n);function n(e){var a;return Object(l.a)(this,n),(a=t.call(this,e)).state={posts:[],errorMsg:""},a}return Object(u.a)(n,[{key:"componentDidMount",value:function(){var e=this;p.a.get("http://localhost:8080/company").then((function(t){console.log(t),e.setState({posts:t.data})})).catch((function(t){console.log(t),e.setState({errorMsg:"Keine Daten erhalten"})}))}},{key:"render",value:function(){var e=this.state,t=e.posts,n=e.errorMsg;return Object(a.jsxs)("div",{children:["List of Posts",t.length?t.map((function(e){return Object(a.jsx)("div",{children:e.name},e.id)})):null,n?Object(a.jsx)("div",{children:n}):null]})}}]),n}(s.Component),v=function(e){Object(d.a)(n,e);var t=Object(j.a)(n);function n(){var e;return Object(l.a)(this,n),(e=t.call(this)).Changehandler=function(t){e.setState(Object(i.a)({},t.target.name,t.target.value))},e.handleSubmit=function(t){t.preventDefault(),console.log(e.state),p.a.put("http://localhost:8080/user/login",e.state).then((function(e){console.log(e)})).catch((function(e){console.log(e)}))},e.state={username:"",password:""},e.handleSubmit=e.handleSubmit.bind(Object(h.a)(e)),e}return Object(u.a)(n,[{key:"render",value:function(){return Object(a.jsx)("div",{className:"user-form-login",children:Object(a.jsxs)("form",{onSubmit:this.handleSubmit,children:[Object(a.jsxs)("div",{children:[Object(a.jsxs)("div",{className:"user-input",children:[Object(a.jsx)("label",{children:"Username"}),Object(a.jsx)("input",{placeholder:"Username",name:"username",type:"text",value:this.state.username,onChange:this.Changehandler})]}),Object(a.jsxs)("div",{className:"user-input",children:[Object(a.jsx)("label",{children:"Passwort"}),Object(a.jsx)("input",{placeholder:"Passwort",name:"password",type:"password",value:this.state.password,onChange:this.Changehandler})]})]}),Object(a.jsx)("div",{children:Object(a.jsx)("input",{type:"submit",value:"Login"})})]})})}}]),n}(s.Component);r.a.render(Object(a.jsxs)(c.a,{children:[Object(a.jsx)(m,{}),Object(a.jsx)(v,{})]}),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()}))}},[[59,1,2]]]);
//# sourceMappingURL=main.d244a1fb.chunk.js.map