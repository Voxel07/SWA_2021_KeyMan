(this.webpackJsonpwebapp=this.webpackJsonpwebapp||[]).push([[0],{33:function(t,n,e){},34:function(t,n,e){},35:function(t,n,e){},54:function(t,n,e){},60:function(t,n,e){"use strict";e.r(n);var o=e(0),r=e(1),a=e(25),c=e.n(a),s=e(27);e(33),e.p,e(34);var i=e(11),l=e(4),u=e(5),h=e(9),p=e(8),d=e(7),v=(e(35),e(6)),f=e.n(v);r.Component,Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));r.Component,e(54),r.Component;var j=function(t){Object(p.a)(e,t);var n=Object(d.a)(e);function e(t){var o;return Object(l.a)(this,e),(o=n.call(this,t)).state={posts:[],errorMsg:""},o}return Object(u.a)(e,[{key:"componentDidMount",value:function(){var t=this;f.a.get("http://localhost:8080/contract").then((function(n){console.log(n),t.setState({posts:n.data})})).catch((function(n){console.log(n),t.setState({errorMsg:" erhalten"})}))}},{key:"render",value:function(){var t=this.state,n=t.posts,e=t.errorMsg;return Object(o.jsxs)("div",{children:["List of Posts",n.length?n.map((function(t){return Object(o.jsx)("div",{children:t.name},t.id)})):null,e?Object(o.jsx)("div",{children:e}):null]})}}]),e}(r.Component);c.a.render(Object(o.jsx)(s.a,{children:Object(o.jsx)(j,{})}),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(t){t.unregister()}))}},[[60,1,2]]]);
//# sourceMappingURL=main.d59b20ca.chunk.js.map