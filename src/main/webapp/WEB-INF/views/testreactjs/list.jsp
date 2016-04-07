<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/global.jsp"%>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="${ctx }/js/reactjs/react.js"></script>
<script src="${ctx }/js/reactjs/react-dom.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.24/browser.min.js"></script>
<%-- <script src="${ctx }/js/reactjs/JSXTransformer.js"></script> --%>

<%-- <script src="${ctx }/js/common/jquery-1.8.3.js"></script> --%>


</head>
 <body>
    <div id="message" align="center"></div>

    <script type="text/babel">
      var Counter = React.createClass({
        getInitialState: function () {
          return { clickCount: 0 };
        },
        handleClick: function () {
          this.setState(function(state) {
            return {clickCount: state.clickCount + 1};
          });
        },
        render: function () {
          return (<h2 onClick={this.handleClick}>Click me! Number of clicks: {this.state.clickCount}</h2>);
        }
      });
      ReactDOM.render(
        <Counter />,
        document.getElementById('message')
      );
    </script>
  </body>
</html>